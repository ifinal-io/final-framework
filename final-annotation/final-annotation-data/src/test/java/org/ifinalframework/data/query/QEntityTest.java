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

package org.ifinalframework.data.query;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QProperty;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * QEntityTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@ExtendWith({MockitoExtension.class})
class QEntityTest {

    @Spy
    private QEntity entity;

    @Mock
    private QProperty property;

    @Test
    void getName() {
        when(entity.getType()).thenReturn(QEntity.class);
        assertEquals(QEntity.class.getName(),entity.getName());
        assertEquals(QEntity.class.getSimpleName(),entity.getSimpleName());

        when(entity.getVersionProperty()).thenReturn(null);
        assertFalse(entity.hasVersionProperty());

        when(entity.getProperty(anyString())).thenReturn(null);
        assertThrows(IllegalStateException.class,() -> entity.getRequiredProperty("path"));


        when(entity.getProperty(anyString())).thenReturn(property);
        assertEquals(property,entity.getRequiredProperty("path"));


    }

}