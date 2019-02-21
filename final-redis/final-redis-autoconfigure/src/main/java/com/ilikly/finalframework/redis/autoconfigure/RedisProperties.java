package com.ilikly.finalframework.redis.autoconfigure;

import com.ilikly.finalframework.redis.serializer.Object2JsonRedisSerializer;
import com.ilikly.finalframework.redis.serializer.Object2StringRedisSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-10 15:12:22
 * @since 1.0
 */
@ConfigurationProperties(prefix = RedisProperties.REDIS_PROPERTIES_PREFIX)
public class RedisProperties {
    public static final String REDIS_PROPERTIES_PREFIX = "final.redis.serializer";
    private Class<? extends RedisSerializer> keySerializer = Object2StringRedisSerializer.class;
    private Class<? extends RedisSerializer> valueSerializer = Object2JsonRedisSerializer.class;
    private Class<? extends RedisSerializer> hashKeySerializer = Object2StringRedisSerializer.class;
    private Class<? extends RedisSerializer> hashValueSerializer = Object2JsonRedisSerializer.class;

    public Class<? extends RedisSerializer> getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(Class<? extends RedisSerializer> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public Class<? extends RedisSerializer> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(Class<? extends RedisSerializer> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public Class<? extends RedisSerializer> getHashKeySerializer() {
        return hashKeySerializer;
    }

    public void setHashKeySerializer(Class<? extends RedisSerializer> hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    public Class<? extends RedisSerializer> getHashValueSerializer() {
        return hashValueSerializer;
    }

    public void setHashValueSerializer(Class<? extends RedisSerializer> hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }
}
