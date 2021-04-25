package org.ifinal.finalframework.spiriter.redis.api.controller;

import org.ifinal.finalframework.redis.Redis;
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
 * @version 1.0.0
 *
 * @see org.springframework.data.redis.connection.DataType#STRING
 * @since 1.0.0
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

