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

package org.ifinalframework.data.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;

import org.ifinalframework.json.Json;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A custom {@link RedisSerializer} for {@link Object} serialize to {@linkplain String json} and deserialize to {@link
 * String}.
 *
 * @author iimik
 * @version 1.0.0
 * @see Object2StringRedisSerializer
 * @since 1.0.0
 */
public class Object2JsonRedisSerializer implements RedisSerializer<Object> {

    public static final Object2JsonRedisSerializer UTF_8 = new Object2JsonRedisSerializer();

    private final Charset charset;

    public Object2JsonRedisSerializer(final Charset charset) {
        this.charset = charset;
    }

    public Object2JsonRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(final Object o) {
        return o == null ? null : Json.toJson(o).getBytes(charset);
    }

    @Override
    public Object deserialize(final byte[] bytes) {
        return bytes == null ? null : new String(bytes, charset);
    }

}
