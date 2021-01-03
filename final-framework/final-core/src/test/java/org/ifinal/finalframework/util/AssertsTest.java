package org.ifinal.finalframework.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * AssertsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class AssertsTest {

    @Test
    void isNull() {
        assertTrue(Asserts.isNull(null));
        assertFalse(Asserts.isNull(1));
    }

    @Test
    void requiredNull() {
        assertDoesNotThrow(() -> Asserts.requiredNull(null));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNull(1));
    }

    @Test
    void nonNull() {
        assertTrue(Asserts.nonNull(1));
        assertFalse(Asserts.nonNull(null));
    }

    @Test
    void requiredNonNull() {
        assertNotNull(Asserts.requiredNonNull(new Object()));
        assertNotNull(Asserts.requiredNonNull(new Object(), "need null value"));
        assertThrows(NullPointerException.class, () -> Asserts.requiredNonNull(null));
    }

    @Test
    void isTrue() {
        assertTrue(Asserts.isTrue(true));
    }

    @Test
    void requiredTure() {
        assertTrue(Asserts.requiredTrue(true));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredTrue(false));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredTrue(1));
    }

    @Test
    void isFalse() {
        assertTrue(Asserts.isFalse(false));
        assertFalse(Asserts.isFalse(true));
    }

    @Test
    void requiredFalse() {
        assertFalse(Asserts.requiredFalse(false));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredFalse(null));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredFalse(true));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredFalse(1));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredFalse(0));
    }

    @Test
    void isEmpty() {
        assertTrue(Asserts.isEmpty(null));
        assertTrue(Asserts.isEmpty(""));
        assertTrue(Asserts.isEmpty(new ArrayList<>()));
        assertTrue(Asserts.isEmpty(new HashMap<>()));
        assertTrue(Asserts.isEmpty(new String[]{}));
    }

    @Test
    void nonEmpty() {
        assertFalse(Asserts.nonEmpty(null));
        assertTrue(Asserts.nonEmpty(" "));
        assertTrue(Asserts.nonEmpty(Collections.singleton(1)));
        assertTrue(Asserts.nonEmpty(Collections.singletonMap("1", 1)));
        assertTrue(Asserts.nonEmpty(new String[]{"1"}));
    }

    @Test
    void requiredNonEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonEmpty(null));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonEmpty(""));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonEmpty(Collections.emptyList()));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonEmpty(Collections.emptyMap()));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonEmpty(new String[]{}));
        assertDoesNotThrow(() -> Asserts.requiredNonEmpty(" "));
        assertDoesNotThrow(() -> Asserts.requiredNonEmpty(Collections.singleton(1)));
        assertDoesNotThrow(() -> Asserts.requiredNonEmpty(Collections.singletonMap(1, "1")));
        assertDoesNotThrow(() -> Asserts.requiredNonEmpty(new String[]{"1"}));
    }

    @Test
    void isBlank() {
        assertTrue(Asserts.isBlank(null));
        assertTrue(Asserts.isBlank(""));
        assertTrue(Asserts.isBlank(" "));
        assertTrue(Asserts.isBlank("   "));
    }

    @Test
    void nonBlank() {
        assertFalse(Asserts.nonBlank(null));
        assertFalse(Asserts.nonBlank(" "));
        assertFalse(Asserts.nonBlank("  "));
        assertTrue(Asserts.nonBlank("!"));
    }

    @Test
    void requiredNonBlank() {
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonBlank(null));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonBlank(""));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonBlank(" "));
        assertThrows(IllegalArgumentException.class, () -> Asserts.requiredNonBlank("  "));
        assertEquals("!", Asserts.requiredNonBlank("!"));
    }

    @Test
    void equals() {
        assertTrue(Asserts.isEquals(null, null));
        assertTrue(Asserts.isEquals(1, 1));
        assertFalse(Asserts.isEquals(null, 1));
    }

    @Test
    void nonEquals() {
        assertTrue(Asserts.nonEquals(null, 1));
    }

}