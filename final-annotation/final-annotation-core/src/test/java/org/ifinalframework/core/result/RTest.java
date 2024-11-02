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

package org.ifinalframework.core.result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class RTest {

    @Test
    void instance() {
        InvocationTargetException illegalAccessError = assertThrows(InvocationTargetException.class, () -> {
            Constructor<R> constructor = R.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            R instance = constructor.newInstance();
        });
        assertEquals("There is no instance for you!", illegalAccessError.getTargetException().getMessage());

    }

    @Test
    void success() {
        Result<?> result = R.success();
        assertTrue(result.isSuccess());
        assertNull(result.getData());
        assertNull(result.getPagination());
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"a", "B"}
    )
    void successWithData(String data) {
        Result<String> result = R.success(data);
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertNull(result.getPagination());
        assertEquals(data, result.getData());
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"a", "B"}
    )
    void successWithCodeAndData(String data) {
        Result<String> result = R.success(data, data, data);
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertNull(result.getPagination());
        assertEquals(data, result.getData());
        assertEquals(data, result.getCode());
        assertEquals(data, result.getMessage());
    }

    @Test
    void failure() {
        Result<?> result = R.failure(123, "123");
        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertNull(result.getPagination());
        assertEquals(123, result.getStatus());
        assertEquals("123", result.getDescription());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void failureWithCode(int status) {
        String message = String.valueOf(status);
        Result<?> result = R.failure(status, message, message, message);
        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertNull(result.getPagination());
        assertEquals(status, result.getStatus());
        assertEquals(message, result.getDescription());
        assertEquals(message, result.getCode());
        assertEquals(message, result.getMessage());
    }

}
