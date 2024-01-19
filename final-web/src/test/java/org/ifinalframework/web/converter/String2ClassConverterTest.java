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

package org.ifinalframework.web.converter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import org.ifinalframework.context.exception.NotFoundException;

import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * String2ClassConverterTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
class String2ClassConverterTest {

    private final String2ClassConverter converter = new String2ClassConverter();

    @ParameterizedTest
    @ValueSource(
            classes = {
                    RestController.class, Controller.class,
                    Component.class, Service.class, Configuration.class,
                    EnableAutoConfiguration.class, Aspect.class
            })
    void convert(Class clazz) {

        assertEquals(clazz, converter.convert(clazz.getSimpleName()));

        assertEquals(String2ClassConverterTest.class, converter.convert(String2ClassConverterTest.class.getName()));

    }

    @Test
    void throwException() {
        assertThrows(NotFoundException.class, () -> converter.convert("a"));
    }

}
