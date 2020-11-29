package org.ifinal.finalframework.devops.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClassDumpTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(ClassDumpTransformer.class);

    private static final String CLASS_DUMP_DIR = "classDump";

    private final Set<Class<?>> classesToEnhance;
    private final Map<Class<?>, File> dumpResult;

    private final File directory;

    public ClassDumpTransformer(Set<Class<?>> classesToEnhance) {
        this(classesToEnhance, null);
    }

    public ClassDumpTransformer(Set<Class<?>> classesToEnhance, File directory) {
        this.classesToEnhance = classesToEnhance;
        this.dumpResult = new HashMap<>();
        this.directory = directory;
    }

    @Override
    @Nullable
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (classesToEnhance.contains(classBeingRedefined)) {
            dumpClassIfNecessary(classBeingRedefined, classfileBuffer);
        }
        return null;
    }

    public Map<Class<?>, File> getDumpResult() {
        return dumpResult;
    }

    private void dumpClassIfNecessary(Class<?> clazz, byte[] data) {
        String className = clazz.getName();
        ClassLoader classLoader = clazz.getClassLoader();

        // 创建类所在的包路径
        File dumpDir;
        if (directory != null) {
            dumpDir = directory;
        } else {
            dumpDir = new File(CLASS_DUMP_DIR);
        }
        if (!dumpDir.mkdirs() && !dumpDir.exists()) {
            logger.warn("create dump directory:{} failed.", dumpDir.getAbsolutePath());
            return;
        }

        String fileName;
        if (classLoader != null) {
            fileName = classLoader.getClass().getName() + "-" + Integer.toHexString(classLoader.hashCode()) +
                    File.separator + className.replace(".", File.separator) + ".class";
        } else {
            fileName = className.replace(".", File.separator) + ".class";
        }

        File paths = new File(dumpDir, fileName.substring(0, fileName.lastIndexOf("/") + 1));

        if (!paths.exists()) {
            boolean mkdirs = paths.mkdirs();
            logger.info("create calssDump dirs : {}", mkdirs);
        }

        File dumpClassFile = new File(dumpDir, fileName);


        if (dumpClassFile.exists()) {
            boolean delete = dumpClassFile.delete();
            logger.info("delete file {} {}", dumpClassFile.getName(), delete);
        }


        // 将类字节码写入文件
        try (OutputStream out = new FileOutputStream(dumpClassFile)) {
            out.write(data);
            out.flush();
            dumpResult.put(clazz, dumpClassFile);
        } catch (IOException e) {
            logger.warn("dump class:{} to file {} failed.", className, dumpClassFile, e);
        }
    }
}
