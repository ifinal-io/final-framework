/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.java;

import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.java.compiler.Compiler;
import org.ifinalframework.java.compiler.DynamicClassLoader;

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
public final class Redefiner {

    private Redefiner() {

    }

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
