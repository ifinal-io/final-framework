/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.ifinalframework.data.redis.serializer.Object2JsonRedisSerializer;
import org.ifinalframework.data.redis.serializer.Object2StringRedisSerializer;

/**
 * A custom {@link RedisTemplate} which serialize {@code key} to {@code string} and {@code value} to {@code json}.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.springframework.data.redis.core.StringRedisTemplate
 * @see Object2StringRedisSerializer
 * @see Object2JsonRedisSerializer
 * @since 1.0.0
 */
public class ObjectStringJsonRedisTemplate extends RedisTemplate<Object, Object> {

    public ObjectStringJsonRedisTemplate() {
        this.setKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setValueSerializer(Object2JsonRedisSerializer.UTF_8);
        this.setHashKeySerializer(Object2StringRedisSerializer.UTF_8);
        this.setHashValueSerializer(Object2JsonRedisSerializer.UTF_8);
    }

    /**
     * @param redisConnectionFactory redis connection factory
     * @since 1.3.1
     */
    public ObjectStringJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this();
        setConnectionFactory(redisConnectionFactory);
    }

}
