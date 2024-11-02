/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.bytecode.ClassFile;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.javassist.JavaAssistProcessor;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * MapperAnnotationBuilderJavaAssistProcessor.
 *
 * @author iimik
 * @version 1.5.1
 * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder
 * @since 1.5.1
 */
@Slf4j
@AutoService(JavaAssistProcessor.class)
public class MapperAnnotationBuilderJavaAssistProcessor implements JavaAssistProcessor {

    @Override
    public void process(ClassPool classPool) throws Throwable {

        logger.debug("start modify class: MapperAnnotationBuilder");
        final CtClass ctClass = classPool.get("org.apache.ibatis.builder.annotation.MapperAnnotationBuilder");
        if (ctClass.isFrozen()) {
            return;
        }
        final CtConstructor constructor = ctClass.getDeclaredConstructors()[0];
        /*
         *   public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
         *     String resource = type.getName().replace('.', '/') + ".java (best guess)";
         *     this.assistant = new MapperBuilderAssistant(configuration, resource);
         *     this.configuration = configuration;
         *     this.type = type;
         *   }
         */
        constructor.setBody("{String resource = $2.getName().replace('.', '/') + \".java (best guess)\";\n"
                + "this.assistant = new org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt($1, resource);\n"
                + "this.configuration = $1;\n"
                + "this.type = $2;\n}"
        );
        if (ClassFile.MAJOR_VERSION > ClassFile.JAVA_8) {
            final Class<?> newClass = ctClass.toClass(ProviderMethodResolver.class);
        } else {
            final Class<?> newClass = ctClass.toClass();
        }
        logger.debug("finish modify class: MapperAnnotationBuilder");
    }
}
