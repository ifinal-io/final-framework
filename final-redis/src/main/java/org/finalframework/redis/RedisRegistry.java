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

package org.finalframework.redis;


import org.springframework.data.redis.core.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:53:01
 * @since 1.0
 */
public final class RedisRegistry {
    private static final RedisRegistry instance = new RedisRegistry();
    private RedisTemplate redisTemplate;

    private RedisRegistry() {
    }

    public static RedisRegistry getInstance() {
        return instance;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<?, ?> template() {
        return redisTemplate;
    }

    public RedisOperations key() {
        return redisTemplate;
    }

    public ValueOperations value() {
        return redisTemplate.opsForValue();
    }

    public HashOperations hash() {
        return redisTemplate.opsForHash();
    }

    public ListOperations list() {
        return redisTemplate.opsForList();
    }

    public SetOperations set() {
        return redisTemplate.opsForSet();
    }

    public ZSetOperations zset() {
        return redisTemplate.opsForZSet();
    }


}
