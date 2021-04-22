package org.ifinal.finalframework.redis.serializer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Object2JsonRedisSerializerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class Object2JsonRedisSerializerTest {

    @Test
    void serialize() {
        assertNull(Object2JsonRedisSerializer.UTF_8.serialize(null));
        assertArrayEquals("2".getBytes(StandardCharsets.UTF_8), Object2StringRedisSerializer.UTF_8.serialize(2));
    }

    @Test
    void deserialize() {
        assertNull(Object2JsonRedisSerializer.UTF_8.deserialize(null));
        assertEquals("2", Object2StringRedisSerializer.UTF_8.deserialize("2".getBytes(StandardCharsets.UTF_8)));
    }

}
