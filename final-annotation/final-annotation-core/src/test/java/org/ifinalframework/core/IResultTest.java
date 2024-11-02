/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * IResultTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class IResultTest {

    @Spy
    private IResult<?> result;

    @Mock
    private IPagination pagination;

    @Test
    void isSuccess() {

        when(result.getStatus()).thenReturn(ResponseStatus.SUCCESS.getCode());
        assertTrue(result.isSuccess());

    }

    @Test
    void should_not_hasMore_when_pagination_is_null() {
        when(result.getPagination()).thenReturn(null);
        assertFalse(result.hasMore());
    }

    @Test
    void should_hasMore_when_pagination_is_not_lastPage() {

        when(pagination.getLastPage()).thenReturn(null);
        when(result.getPagination()).thenReturn(pagination);
        assertTrue(result.hasMore());

    }

    @Test
    void should_not_hasMore_when_pagination_is_lastPage() {
        when(pagination.getLastPage()).thenReturn(true);
        when(result.getPagination()).thenReturn(pagination);
        assertFalse(result.hasMore());
    }

    @ParameterizedTest
    @ValueSource(longs = {100L,1000L})
    void total(Long total){
       when(pagination.getTotal()).thenReturn(total);
       when(result.getPagination()).thenReturn(pagination);
       assertEquals(total,result.getTotal());
    }

}
