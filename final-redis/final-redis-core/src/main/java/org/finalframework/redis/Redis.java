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
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:52:35
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public interface Redis {

    static RedisTemplate<?, ?> template() {
        return RedisRegistry.getInstance().template();
    }

    @NonNull
    static RedisOperations key() {
        return RedisRegistry.getInstance().key();
    }

    static ValueOperations value() {
        return RedisRegistry.getInstance().value();
    }

    static HashOperations hash() {
        return RedisRegistry.getInstance().hash();
    }

    static ListOperations list() {
        return RedisRegistry.getInstance().list();
    }

    static SetOperations set() {
        return RedisRegistry.getInstance().set();
    }

    static ZSetOperations zset() {
        return RedisRegistry.getInstance().zset();
    }

    static boolean lock(Object key, Object value, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(value().setIfAbsent(key, value, timeout, unit));
    }

    static boolean unlock(Object key, Object value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return Boolean.TRUE.equals(key().execute(new DefaultRedisScript<>(script, Boolean.class), Collections.singletonList(key), value));
    }

}
