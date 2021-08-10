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

package org.ifinalframework.context.result;

import org.ifinalframework.context.exception.NotFoundException;
import org.ifinalframework.core.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * SimpleResultSupplierTest.
 *
 * @author likly
 * @version 1.2.2
 * @since 1.2.2
 */
@ExtendWith(MockitoExtension.class)
class SimpleResultSupplierTest {
    @InjectMocks
    private SimpleResultSupplier simpleResultSupplier;

    @Mock
    private Supplier<String> supplier;

    @ParameterizedTest
    @NullAndEmptySource
    void success(String value) {

        final Result<String> result = simpleResultSupplier.get(() -> value);
        assertTrue(result.isSuccess());
        assertEquals(value, result.getData());

    }

    @Test
    void result_is_not_success_when_supplier_throw_service_exception() {
        when(supplier.get()).thenThrow(new NotFoundException("not found"));
        final Result<String> result = simpleResultSupplier.get(supplier);
        assertFalse(result.isSuccess());
        assertEquals(404, result.getStatus());
    }


}
