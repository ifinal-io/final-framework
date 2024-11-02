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

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * XMLMapperBuilderJavaAssistProcessor.
 *
 * @author iimik
 * @version 1.5.1
 * @see org.apache.ibatis.builder.xml.XMLMapperBuilder
 * @see org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt
 * @since 1.5.1
 */
@Slf4j
@AutoService(JavaAssistProcessor.class)
public class XMLMapperBuilderJavaAssistProcessor implements JavaAssistProcessor {
    @Override
    public void process(ClassPool classPool) throws Throwable {
        logger.debug("start modify class: XMLMapperBuilder");
        final CtClass ctClass = classPool.get("org.apache.ibatis.builder.xml.XMLMapperBuilder");
        if (ctClass.isFrozen()) {
            return;
        }
        /*
         * @see XMLMapperBuilder#XMLMapperBuilder(XPathParser, Configuration, String, Map)
         */
        final CtConstructor constructor = ctClass.getDeclaredConstructor(new CtClass[]{
                classPool.get("org.apache.ibatis.parsing.XPathParser"),
                classPool.get("org.apache.ibatis.session.Configuration"),
                classPool.get("java.lang.String"),
                classPool.get("java.util.Map")
        });
        /*
         *   private XMLMapperBuilder(XPathParser parser, Configuration configuration, String resource,
         *       Map<String, XNode> sqlFragments) {
         *     super(configuration);
         *     this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
         *     this.parser = parser;
         *     this.sqlFragments = sqlFragments;
         *     this.resource = resource;
         *   }
         */
        constructor.setBody("{\n"
                + "super($2);\n"
                + "this.builderAssistant = new org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt($2, $3);\n"
                + "this.parser = $1;\n"
                + "this.sqlFragments = $4;\n"
                + "this.resource = $3;\n}"
        );

        if (ClassFile.MAJOR_VERSION > ClassFile.JAVA_8) {
            final Class<?> newClass = ctClass.toClass(XMLConfigBuilder.class);
        } else {
            final Class<?> newClass = ctClass.toClass();
        }

        logger.debug("finish modify class: XMLMapperBuilder");
    }
}
