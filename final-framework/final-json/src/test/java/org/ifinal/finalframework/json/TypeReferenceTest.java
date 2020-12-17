package org.ifinal.finalframework.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * TypeReferenceTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class TypeReferenceTest {

    @Test
    void getRawType() {
        assertEquals(String.class, new TypeReference<String>() {
        }.getRawType());

        assertEquals(List.class, new TypeReference<List<String>>() {
        }.getRawType());
    }

    @Test
    void getType() {
        assertEquals(String.class, new TypeReference<String>() {
        }.getType());
        assertTrue(new TypeReference<List<String>>() {
        }.getType() instanceof ParameterizedType);
    }

}