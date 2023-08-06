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

package org.ifinalframework.web.servlet.exception.result;

import org.springframework.web.bind.MissingServletRequestParameterException;

import org.ifinalframework.core.result.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author ilikly
 * @version 1.3.1
 **/
@ExtendWith(MockitoExtension.class)
class MissingServletParameterResultExceptionHandlerTest {

    private final MissingServletParameterResultExceptionHandler handler = new MissingServletParameterResultExceptionHandler();

    @Mock
    private MissingServletRequestParameterException exception;
    @Mock
    private RuntimeException runtimeException;

    @Test
    void supports() {
        assertTrue(handler.supports(exception));
        assertFalse(handler.supports(runtimeException));

    }

    @Test
    void handle() {
        when(exception.getMessage()).thenReturn("haha");

        Result<?> result = handler.handle(exception);
        assertFalse(result.isSuccess());
        assertEquals("haha", result.getMessage());

    }
}