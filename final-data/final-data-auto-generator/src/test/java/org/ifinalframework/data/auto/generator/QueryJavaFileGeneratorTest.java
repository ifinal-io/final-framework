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

package org.ifinalframework.data.auto.generator;

import com.squareup.javapoet.JavaFile;

import org.ifinalframework.core.IView;
import org.ifinalframework.data.annotation.AbsEntity;
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.java.compiler.Compiler;
import org.ifinalframework.java.compiler.DynamicClassLoader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * QueryJavaFileGeneratorTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@Slf4j
class QueryJavaFileGeneratorTest {

    @Test
    @SneakyThrows
    void generate() {
        QueryJavaFileGenerator generator = new QueryJavaFileGenerator();
        AutoService autoService = Mockito.mock(AutoService.class);
        JavaFile javaFile = generator.generate(autoService, AbsEntity.class);
        Compiler compiler = new Compiler(getClass().getClassLoader());
        String name = generator.getName(autoService, AbsEntity.class);
        compiler.addSource(name, javaFile.toString());
        DynamicClassLoader compile = compiler.compile();
        Class<?> query = compile.getClasses().get(name);
        Assertions.assertTrue(PageQuery.class.isAssignableFrom(query));
    }

    @Test
    @SneakyThrows
    void generateListQuery() {
        QueryJavaFileGenerator generator = new QueryJavaFileGenerator(IView.List.class, PageQuery.class, false);
        AutoService autoService = Mockito.mock(AutoService.class);
        JavaFile javaFile = generator.generate(autoService, AbsEntity.class);
        Compiler compiler = new Compiler(getClass().getClassLoader());
        String name = generator.getName(autoService, AbsEntity.class);
        Assertions.assertTrue(name.contains("AbsEntityListQuery"));
        final String javaSource = javaFile.toString();
        logger.info(javaSource);
        compiler.addSource(name, javaSource);
        DynamicClassLoader compile = compiler.compile();
        Class<?> query = compile.getClasses().get(name);
        Assertions.assertTrue(PageQuery.class.isAssignableFrom(query));
    }
}