package org.finalframework.redis;

import org.finalframework.redis.serializer.Object2JsonRedisSerializer;
import org.finalframework.redis.serializer.Object2StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/16 11:26:26
 * @see org.springframework.data.redis.core.StringRedisTemplate
 * @since 1.0
 */
public class FinalRedisTemplate extends RedisTemplate<Object, Object> {
    public FinalRedisTemplate() {
        this.setKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setValueSerializer(Object2JsonRedisSerializer.UTF_8);
        this.setHashKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setHashValueSerializer(Object2JsonRedisSerializer.UTF_8);
    }
}
