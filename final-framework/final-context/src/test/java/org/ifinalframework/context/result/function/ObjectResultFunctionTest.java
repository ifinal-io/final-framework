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

package org.ifinalframework.context.result.function;

import org.ifinalframework.core.result.Result;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ObjectResultFunctionTest.
 *
 * @author iimik
 * @version 1.2.1
 * @since 1.2.1
 */
@ExtendWith(MockitoExtension.class)
class ObjectResultFunctionTest {

    @InjectMocks
    private ObjectResultFunction objectResultFunction;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"haha"})
    void apply(String value) {
        final Result<?> result = objectResultFunction.apply(value);
        assertNotNull(result);
        assertEquals(value, result.getData());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"haha"})
    void test(String value) {
        assertTrue(objectResultFunction.test(value));
    }

}
