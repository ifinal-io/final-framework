package org.finalframework.redis.serializer;


import org.finalframework.json.Json;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-10 14:25:08
 * @since 1.0
 */
@Component
public class Object2JsonRedisSerializer implements RedisSerializer<Object> {
    public static final Object2JsonRedisSerializer UTF_8 = new Object2JsonRedisSerializer(StandardCharsets.UTF_8);
    private final Charset charset;

    public Object2JsonRedisSerializer(Charset charset) {
        this.charset = charset;
    }

    public Object2JsonRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(Object o) {
        return o == null ? null : Json.toJson(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
