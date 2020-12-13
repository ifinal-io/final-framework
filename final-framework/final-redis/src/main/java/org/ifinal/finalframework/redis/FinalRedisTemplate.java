package org.ifinal.finalframework.redis;

import org.ifinal.finalframework.redis.serializer.Object2JsonRedisSerializer;
import org.ifinal.finalframework.redis.serializer.Object2StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author likly
 * @version 1.0.0
 * @see org.springframework.data.redis.core.StringRedisTemplate
 * @since 1.0.0
 */
public class FinalRedisTemplate extends RedisTemplate<Object, Object> {

    public FinalRedisTemplate() {
        this.setKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setValueSerializer(Object2JsonRedisSerializer.UTF_8);
        this.setHashKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setHashValueSerializer(Object2JsonRedisSerializer.UTF_8);
    }

}
