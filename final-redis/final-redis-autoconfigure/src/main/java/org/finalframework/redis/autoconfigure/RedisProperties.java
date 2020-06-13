/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.redis.autoconfigure;

import org.finalframework.redis.serializer.Object2JsonRedisSerializer;
import org.finalframework.redis.serializer.Object2StringRedisSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-10 15:12:22
 * @since 1.0
 */
@ConfigurationProperties(prefix = RedisProperties.REDIS_PROPERTIES_PREFIX)
public class RedisProperties {
    static final String REDIS_PROPERTIES_PREFIX = "final.redis.serializer";
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
