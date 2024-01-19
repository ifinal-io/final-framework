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

import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Instrumentations {

    private Instrumentations() {
    }

    public static Instrumentation get() {

        return ByteBuddyAgent.install();
    }

    public static void retransformClasses(final Instrumentation inst, final ClassFileTransformer transformer,
                                          final Set<Class<?>> classes) {


        try {
            inst.addTransformer(transformer, true);

            for (Class<?> clazz : classes) {
                if (isLambdaClass(clazz)) {
                    logger.info(
                            "ignore lambda class: {}, because jdk do not support retransform lambda class: https://github.com/alibaba/arthas/issues/1512.",
                            clazz.getName());
                    continue;
                }
                try {
                    inst.retransformClasses(clazz);
                } catch (Exception e) {
                    String errorMsg = "retransform Classes class error, name: " + clazz.getName();
                    logger.error(errorMsg, e);
                }
            }
        } finally {
            inst.removeTransformer(transformer);
        }
    }

    public static boolean isLambdaClass(final Class<?> clazz) {

        return clazz.getName().contains("$$Lambda$");
    }

}
