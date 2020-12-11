package org.ifinal.finalframework.devops.java;

import lombok.extern.slf4j.Slf4j;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.ifinal.finalframework.util.Asserts;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Decompiler {
    private Decompiler() {
    }

    public static String decompile(Class<?> clazz, String methodName) {
        Instrumentation instrumentation = Instrumentations.get();

        HashSet<Class<?>> classes = new HashSet<>(Collections.singletonList(clazz));
        URL root = ClassLoader.getSystemClassLoader().getResource(".");
        Objects.requireNonNull(root);
        ClassDumpTransformer transformer = new ClassDumpTransformer(classes, new File(root.getPath()));
        Instrumentations.retransformClasses(instrumentation, transformer, classes);
        Map<Class<?>, File> dumpResult = transformer.getDumpResult();
        File file = dumpResult.get(clazz);
        String absolutePath = file.getAbsolutePath();
        String decompile = decompile(absolutePath, methodName);
        boolean delete = file.delete();
        logger.info("delete decompile file {} {}", file.getName(), delete);
        return decompile;
    }


    public static String decompile(String classFilePath, String methodName) {
        return decompile(classFilePath, methodName, false);
    }

    public static String decompile(String classFilePath, String methodName, boolean hideUnicode) {
        final StringBuilder result = new StringBuilder(8192);

        OutputSinkFactory mySink = new OutputSinkFactory() {
            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                return Arrays.asList(SinkClass.STRING, SinkClass.DECOMPILED, SinkClass.DECOMPILED_MULTIVER,
                        SinkClass.EXCEPTION_MESSAGE);
            }

            @Override
            public <T> Sink<T> getSink(final SinkType sinkType, SinkClass sinkClass) {
                return sinkable -> {
                    // skip message like: Analysing type demo.MathGame
                    if (sinkType == SinkType.PROGRESS) {
                        return;
                    }
                    result.append(sinkable);
                };
            }
        };

        HashMap<String, String> options = new HashMap<>();
        options.put("showversion", "false");
        options.put("hideutf", String.valueOf(hideUnicode));
        if (Asserts.nonBlank(methodName)) {
            options.put("methodname", methodName);
        }

        CfrDriver driver = new CfrDriver.Builder().withOptions(options).withOutputSink(mySink).build();
        List<String> toAnalyse = new ArrayList<>();
        toAnalyse.add(classFilePath);
        driver.analyse(toAnalyse);

        return result.toString();
    }
}
