package org.ifinal.finalframework.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * PrimariesTest.
 *
 * @author likly
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