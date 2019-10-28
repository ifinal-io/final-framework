package org.finalframework.redis.api.controller;

import org.finalframework.coding.spring.AutoConfiguration;
import org.finalframework.redis.Redis;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-28 17:35:10
 * @since 1.0
 */
@AutoConfiguration
@RestController
@RequestMapping("/api/redis/keys")
@SuppressWarnings("unchecked")
public class RedisKeyApiController {

    @GetMapping
    public Collection<String> keys(@RequestParam(value = "key", required = false, defaultValue = "*") String key) {
        return Redis.key().keys(key);
    }

    @GetMapping("/ttl")
    public Long tti(@RequestParam("key") String key) {
        return Redis.key().getExpire(key);
    }

    /**
     * 删除缓存keys
     *
     * @param keys
     * @return
     */
    @DeleteMapping
    public Long delete(@RequestParam("keys") List<String> keys) {
        return Redis.key().delete(keys);
    }

    @PostMapping("delete")
    public Long deleteKeys(@RequestParam("keys") List<String> keys) {
        return delete(keys);
    }
}
