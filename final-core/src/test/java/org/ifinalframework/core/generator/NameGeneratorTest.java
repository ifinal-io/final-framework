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

package org.ifinalframework.core.generator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * NameGeneratorTest.
 *
 * @author ilikly
 * @version 1.2.4
 * @since 1.2.4
 */
class NameGeneratorTest {

    @ParameterizedTest
    @NullAndEmptySource
    void capitalize_exception(String name) {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> NameGenerator.capitalize(name));
        assertEquals("the name must be not empty!", exception.getMessage());
    }

    @Test
    void capitalize() {
        assertEquals("XiaoMing", NameGenerator.capitalize("xiaoMing"));
        assertEquals("setName", NameGenerator.capitalize("set", "name"));
    }

    @Test
    void decapitalize() {
        assertEquals("name", NameGenerator.decapitalize("setName", "set"));
    }
}
