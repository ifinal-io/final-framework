package org.ifinal.finalframework.redis.serializer;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Object2StringRedisSerializer implements RedisSerializer<Object> {
    public static final Object2StringRedisSerializer UTF_8 = new Object2StringRedisSerializer(StandardCharsets.UTF_8);
    private final Charset charset;

    public Object2StringRedisSerializer(Charset charset) {
        this.charset = charset;
    }

    public Object2StringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return o == null ? null : o.toString().getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
