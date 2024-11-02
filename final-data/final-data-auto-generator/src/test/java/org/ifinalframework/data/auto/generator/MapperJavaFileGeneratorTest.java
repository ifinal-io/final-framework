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

import org.ifinalframework.data.annotation.AbsEntity;
import org.ifinalframework.data.auto.annotation.AutoMapper;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.java.compiler.Compiler;
import org.ifinalframework.java.compiler.DynamicClassLoader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lombok.SneakyThrows;

/**
 * MapperJavaFileGeneratorTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
class MapperJavaFileGeneratorTest {

    private final MapperJavaFileGenerator generator = new MapperJavaFileGenerator();

    @Test
    @SneakyThrows
    void generate() {
        AutoMapper autoMapper = Mockito.mock(AutoMapper.class);
        Class<AbsEntity> clazz = AbsEntity.class;
        JavaFile javaFile = generator.generate(autoMapper, clazz);
        Compiler compiler = new Compiler(getClass().getClassLoader());
        String name = generator.getName(autoMapper, clazz);
        compiler.addSource(name, javaFile.toString());
        DynamicClassLoader loader = compiler.compile();
        Class<?> mapper = loader.getClasses().get(name);
        Assertions.assertTrue(AbsMapper.class.isAssignableFrom(mapper));
    }
}