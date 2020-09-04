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

package org.finalframework.redis.serializer;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-10 14:25:08
 * @since 1.0
 */
@SpringComponent
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
