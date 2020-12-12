package org.ifinal.finalframework.devops.java;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.devops.java.compiler.Compiler;
import org.ifinal.finalframework.devops.java.compiler.DynamicClassLoader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public final class Redefiner {

    public static void redefine(final Class<?> clazz, final String source) {

        Instrumentation instrumentation = Instrumentations.get();

        ClassLoader classLoader = clazz.getClassLoader();

        Compiler compiler = new Compiler(classLoader);

        String className = clazz.getCanonicalName();
        compiler.addSource(className, source);


        DynamicClassLoader compile = compiler.compile();
        Map<String, byte[]> byteCodes = compile.getByteCodes();


        final List<ClassDefinition> definitions = new ArrayList<>();

        for (Class<?> loadedClass : instrumentation.getAllLoadedClasses()) {
            if (byteCodes.containsKey(loadedClass.getName())) {

                if (!loadedClass.getClassLoader().equals(classLoader)) {
                    continue;
                }


                logger.info("try to redefine class {}", loadedClass.getName());
                definitions.add(new ClassDefinition(loadedClass, byteCodes.get(loadedClass.getName())));
            }
        }


        try {
            instrumentation.redefineClasses(definitions.toArray(new ClassDefinition[0]));
        } catch (Exception e) {
            logger.error("redefine error! " + e.getMessage(), e);
        }


    }
}
