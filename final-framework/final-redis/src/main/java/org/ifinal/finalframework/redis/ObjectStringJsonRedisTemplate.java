package org.ifinal.finalframework.redis;

import org.springframework.data.redis.core.RedisTemplate;

import org.ifinal.finalframework.redis.serializer.Object2JsonRedisSerializer;
import org.ifinal.finalframework.redis.serializer.Object2StringRedisSerializer;

/**
 * A custom {@link RedisTemplate} which serialize {@code key} to {@code string} and {@code value} to {@code json}.
 *
 * @author likly
 * @version 1.0.0
 * @see org.springframework.data.redis.core.StringRedisTemplate
 * @since 1.0.0
 */
public class ObjectStringJsonRedisTemplate extends RedisTemplate<Object, Object> {

    public ObjectStringJsonRedisTemplate() {
        this.setKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setValueSerializer(Object2JsonRedisSerializer.UTF_8);
        this.setHashKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setHashValueSerializer(Object2JsonRedisSerializer.UTF_8);
    }

}
