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

import org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt;
import org.ifinalframework.util.Reflections;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.lang.reflect.Field;

import lombok.SneakyThrows;


/**
 * XMLMapperBuilderJavaAssistProcessorTest.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
class XMLMapperBuilderJavaAssistProcessorTest {

    @SneakyThrows
    @Test
    void process() {
        new XMLMapperBuilderJavaAssistProcessor().process(ClassPool.getDefault());
        final XMLMapperBuilder builder = new XMLMapperBuilder(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"
                + "<mapper namespace=\"Mapper\"></mapper>"), new Configuration(), null, null);
        final Field builderAssistant = Reflections.findField(XMLMapperBuilder.class, "builderAssistant");
        builderAssistant.setAccessible(true);
        final Object o = builderAssistant.get(builder);
        Assertions.assertInstanceOf(MapperBuilderAssistantExt.class, o);
    }
}