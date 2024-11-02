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
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.service.AbsService;
import org.ifinalframework.java.compiler.Compiler;
import org.ifinalframework.java.compiler.DynamicClassLoader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.SneakyThrows;

/**
 * ServiceJavaFileGeneratorTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@ExtendWith(MockitoExtension.class)
class ServiceJavaFileGeneratorTest {

    private final ServiceJavaFileGenerator serviceJavaFileGenerator = new ServiceJavaFileGenerator();

    @Mock
    private AutoService autoService;

    @Test
    void getName() {
        Class<AbsEntity> clazz = AbsEntity.class;
        String name = serviceJavaFileGenerator.getName(autoService, clazz);
        String expected = String.join(".", DomainNameHelper.servicePackage(clazz), DomainNameHelper.serviceName(clazz));
        Assertions.assertEquals(expected, name);
    }

    @Test
    @SneakyThrows
    void generate() {
        Class<AbsEntity> entityClass = AbsEntity.class;
        String serviceName = serviceJavaFileGenerator.getName(autoService, entityClass);
        JavaFile serviceJavaFile = serviceJavaFileGenerator.generate(autoService, entityClass);
        Compiler compiler = new Compiler(getClass().getClassLoader());
        compiler.addSource(serviceName, serviceJavaFile.toString());
        DynamicClassLoader loader = compiler.compile();
        Class<?> clazz = loader.getClasses().get(serviceName);
        Assertions.assertTrue(AbsService.class.isAssignableFrom(clazz));
    }
}