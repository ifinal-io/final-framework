/*
 * Copyright 2020-2022 the original author or authors.
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

import net.bytebuddy.agent.ByteBuddyAgent;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import org.ifinalframework.java.compiler.Compiler;
import org.ifinalframework.java.compiler.DynamicClassLoader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JVMDriver.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
@UtilityClass
public final class JvmDriver {

    private static final Instrumentation instrumentation = ByteBuddyAgent.install();

    private static final JadDriver JAD_DRIVER = new JadDriver();

    public static byte[] dump(String clazz) throws ClassNotFoundException {
        return dump(ClassUtils.forName(clazz, JvmDriver.class.getClassLoader()));
    }

    public static byte[] dump(Class<?> clazz) {
        return DumpDriver.dump(clazz);
    }

    public static String jad(String clazz) throws ClassNotFoundException {
        return jad(clazz, null);
    }

    public static String jad(String clazz, String method) throws ClassNotFoundException {
        return jad(ClassUtils.forName(clazz, JvmDriver.class.getClassLoader()));
    }


    public static String jad(Class<?> clazz) {
        return jad(clazz, null);
    }

    public static String jad(Class<?> clazz, @Nullable String method) {
        return JAD_DRIVER.jad(clazz, method);
    }

    public static byte[] compile(Class<?> clazz, String source) {
        ClassLoader classLoader = clazz.getClassLoader();

        Compiler compiler = new Compiler(classLoader);

        String className = clazz.getCanonicalName();
        compiler.addSource(className, source);

        DynamicClassLoader compile = compiler.compile();
        Map<String, byte[]> byteCodes = compile.getByteCodes();
        return byteCodes.get(className);
    }

    public static void redefine(Class<?> clazz, String source) throws UnmodifiableClassException, ClassNotFoundException {
        redefine(clazz, compile(clazz, source));
    }

    public static void redefine(Class<?> clazz, byte[] bytes) throws UnmodifiableClassException, ClassNotFoundException {
        final List<ClassDefinition> definitions = new ArrayList<>();

        for (Class<?> loadedClass : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(loadedClass.getName())) {

                if (!loadedClass.getClassLoader().equals(clazz.getClassLoader())) {
                    continue;
                }
                definitions.add(new ClassDefinition(loadedClass, bytes));
            }
        }

        instrumentation.redefineClasses(definitions.toArray(new ClassDefinition[0]));

    }

}
