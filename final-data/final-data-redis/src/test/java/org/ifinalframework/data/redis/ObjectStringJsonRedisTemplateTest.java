/*
 * Copyright 2020-2022 the original author or authors.
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

import org.ifinalframework.data.redis.serializer.Object2JsonRedisSerializer;
import org.ifinalframework.data.redis.serializer.Object2StringRedisSerializer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ObjectStringJsonRedisTemplateTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@ExtendWith(MockitoExtension.class)
class ObjectStringJsonRedisTemplateTest {

    @Test
    void instance() {
        RedisConnectionFactory factory = mock(RedisConnectionFactory.class);
        ObjectStringJsonRedisTemplate template = new ObjectStringJsonRedisTemplate(factory);
        assertTrue(template.getKeySerializer() instanceof Object2StringRedisSerializer);
        assertTrue(template.getValueSerializer() instanceof Object2JsonRedisSerializer);
        assertTrue(template.getHashKeySerializer() instanceof Object2StringRedisSerializer);
        assertTrue(template.getHashValueSerializer() instanceof Object2JsonRedisSerializer);
    }

}