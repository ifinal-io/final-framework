package org.ifinal.finalframework.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * ClassesTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ClassesTest {

    @Test
    void forName() {
        assertEquals(Object.class, Classes.forName(Object.class.getName()));
        assertNull(Classes.forName("java.lang.String2"));
        assertThrows(NullPointerException.class, () -> Classes.requiredForName("java.lang.String2"));
    }

}
