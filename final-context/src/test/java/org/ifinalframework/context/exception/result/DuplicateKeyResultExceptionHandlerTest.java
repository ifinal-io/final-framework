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

package org.ifinalframework.context.exception.result;

import org.springframework.dao.DuplicateKeyException;

import org.ifinalframework.core.result.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ilikly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class DuplicateKeyResultExceptionHandlerTest {

    private final DuplicateKeyResultExceptionHandler handler = new DuplicateKeyResultExceptionHandler();

    @Test
    void supports() {
        assertTrue(handler.supports(new DuplicateKeyException("test")));
    }

    @Test
    void handle() {
        Result<?> result = handler.handle(new DuplicateKeyException("test"));
        assertFalse(result.isSuccess());
        assertEquals(500, result.getStatus());
        assertEquals("test", result.getMessage());
    }
}