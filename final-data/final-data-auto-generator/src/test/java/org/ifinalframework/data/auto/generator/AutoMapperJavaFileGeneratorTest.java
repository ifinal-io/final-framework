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

import org.ifinalframework.data.auto.annotation.AutoMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * AutoMapperJavaFileGeneratorTest.
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
class AutoMapperJavaFileGeneratorTest {

    @Test
    @SneakyThrows
    void generate() {
        MapperJavaFileGenerator generator = new MapperJavaFileGenerator();
        JavaFile javaFile = generator.generate(Mockito.mock(AutoMapper.class), AutoEntity.class);
        logger.info("{}", javaFile);
    }
}