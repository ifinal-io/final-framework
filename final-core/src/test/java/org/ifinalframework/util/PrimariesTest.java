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
 */

package org.ifinalframework.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PrimariesTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class PrimariesTest {

    @Test
    void isByte() {
        assertTrue(Primaries.isByte(byte.class));
        assertTrue(Primaries.isByte(Byte.class));
    }

    @Test
    void isChar() {
        assertTrue(Primaries.isChar(char.class));
        assertTrue(Primaries.isChar(Character.class));
    }

    @Test
    void isBoolean() {
        assertTrue(Primaries.isBoolean(boolean.class));
        assertTrue(Primaries.isBoolean(Boolean.class));
    }

    @Test
    void isShort() {
        assertTrue(Primaries.isShort(short.class));
        assertTrue(Primaries.isShort(Short.class));
    }

    @Test
    void isInt() {
        assertTrue(Primaries.isInteger(int.class));
        assertTrue(Primaries.isInteger(Integer.class));
    }

    @Test
    void isLong() {
        assertTrue(Primaries.isLong(long.class));
        assertTrue(Primaries.isLong(Long.class));
    }

    @Test
    void isFloat() {
        assertTrue(Primaries.isFloat(float.class));
        assertTrue(Primaries.isFloat(Float.class));
    }

    @Test
    void isDouble() {
        assertTrue(Primaries.isDouble(double.class));
        assertTrue(Primaries.isDouble(Double.class));
    }

    @Test
    void isString() {
        assertTrue(Primaries.isString(String.class));
    }

    @Test
    void isPrimary() {
        assertTrue(Primaries.isPrimary(byte.class));
        assertTrue(Primaries.isPrimary(Byte.class));
        assertTrue(Primaries.isPrimary(char.class));
        assertTrue(Primaries.isPrimary(Character.class));
        assertTrue(Primaries.isPrimary(boolean.class));
        assertTrue(Primaries.isPrimary(Boolean.class));
        assertTrue(Primaries.isPrimary(short.class));
        assertTrue(Primaries.isPrimary(Short.class));
        assertTrue(Primaries.isPrimary(int.class));
        assertTrue(Primaries.isPrimary(Integer.class));
        assertTrue(Primaries.isPrimary(long.class));
        assertTrue(Primaries.isPrimary(Long.class));
        assertTrue(Primaries.isPrimary(float.class));
        assertTrue(Primaries.isPrimary(Float.class));
        assertTrue(Primaries.isPrimary(double.class));
        assertTrue(Primaries.isPrimary(Double.class));
        assertTrue(Primaries.isPrimary(String.class));
    }

}
