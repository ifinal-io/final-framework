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

package org.finalframework.spiriter.redis.api.controller;

import org.finalframework.redis.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 16:22:25
 * @see org.springframework.data.redis.connection.DataType#STRING
 * @since 1.0
 */
@RestController
@RequestMapping("/api/redis")
@SuppressWarnings("unchecked")
public class RedisValueApiController {
    public static final Logger logger = LoggerFactory.getLogger(RedisValueApiController.class);

    /**
     * @param key
     * @param value
     * @return
     * @see <a href="http://doc.redisfans.com/string/append.html">APPEND</a>
     */
    @PostMapping("/append")
    public Integer append(String key, String value) {
        return Redis.value().append(key, value);
    }

    /**
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @see <a href="http://doc.redisfans.com/string/set.html">SET</a>
     */
    public void set(String key, String value,
                    @RequestParam(value = "timeout", required = false) Long timeout,
                    @RequestParam(value = "timeUnit", required = false) TimeUnit unit) {

        if (timeout == null) {
            Redis.value().set(key, value);
        } else {
            Redis.value().set(key, value, timeout, unit);
        }
    }


}

