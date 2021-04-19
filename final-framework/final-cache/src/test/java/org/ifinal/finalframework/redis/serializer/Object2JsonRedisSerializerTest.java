package org.ifinal.finalframework.redis.serializer;

import static org.junit.jupiter.api.Assertions.assertNull;

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
    }

    @Test
    void deserialize() {
        assertNull(Object2JsonRedisSerializer.UTF_8.deserialize(null));
    }

}
