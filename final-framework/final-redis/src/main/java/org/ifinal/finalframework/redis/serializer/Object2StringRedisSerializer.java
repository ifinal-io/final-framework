package org.ifinal.finalframework.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A custom {@link RedisSerializer} for serialize {@link Object} to {@link String} and deserialize to {@link String}.
 *
 * @author likly
 * @version 1.0.0
 * @see Object2JsonRedisSerializer
 * @since 1.0.0
 */
public class Object2StringRedisSerializer implements RedisSerializer<Object> {

    public static final Object2StringRedisSerializer UTF_8 = new Object2StringRedisSerializer();

    private final Charset charset;

    public Object2StringRedisSerializer(final Charset charset) {
        this.charset = charset;
    }

    public Object2StringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(final Object o) {

        return o == null ? null : o.toString().getBytes(charset);
    }

    @Override
    public Object deserialize(final byte[] bytes) {

        return bytes == null ? null : new String(bytes, charset);
    }

}
