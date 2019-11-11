package org.finalframework.redis.api.controller;

import org.finalframework.redis.Redis;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-28 17:17:56
 * @since 1.0
 */
@SpringConfiguration
@RestController
@RequestMapping("/api/redis/values")
@SuppressWarnings("unchecked")
public class RedisValueApiController {

    @GetMapping
    public Object get(@RequestParam("key") String key) {
        return Redis.value().get(key);
    }

    @PostMapping
    public void post(@RequestParam("key") String key,
                     @RequestParam("value") String value,
                     @RequestParam(value = "ttl", required = false) Long ttl) {
        if (ttl == null) {
            Redis.value().set(key, value);
        } else {
            Redis.value().set(key, value, ttl);
        }
    }


}
