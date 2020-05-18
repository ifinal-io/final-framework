package org.finalframework.spiriter.redis.api.controller;

import org.finalframework.redis.Redis;
import org.finalframework.spiriter.redis.api.model.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-18 18:28:22
 * @since 1.0
 */
@RestController
@RequestMapping("/api/redis")
@SuppressWarnings("unchecked")
public class RedisKeyApiController {
    public static final Logger logger = LoggerFactory.getLogger(RedisKeyApiController.class);

    @GetMapping("/keys")
    public List<RedisKey> keys(@RequestParam("pattern") String pattern) {
        final Set<?> keys = Optional.ofNullable(Redis.key().keys(pattern)).orElse(Collections.EMPTY_SET);
        return keys.stream()
                .map(Object::toString)
                .map(key -> new RedisKey(key, type(key), ttl(key, TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS))
                .collect(Collectors.toList());
    }

    @GetMapping("ttl")
    public Long ttl(@RequestParam("key") String key, @RequestParam(value = "timeUnit", required = false, defaultValue = "MILLISECONDS") TimeUnit timeUnit) {
        return Redis.key().getExpire(key, timeUnit);
    }

    @GetMapping("type")
    public DataType type(@RequestParam("key") String key) {
        return Redis.key().type(key);
    }


}

