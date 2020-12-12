package org.ifinal.finalframework.dashboard.redis.controller.api;

import org.ifinal.finalframework.annotation.auth.Auth;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/hash")
public class RedisHashApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping
    public Object hget(final String key, final String field) {

        if (Asserts.isBlank(field)) {
            return stringRedisTemplate.opsForHash().entries(key);
        }

        return stringRedisTemplate.opsForHash().get(key, field);
    }

    @PostMapping
    public void hset(final String key, final String field, final String value) {

        stringRedisTemplate.opsForHash().put(key, field, value);
    }

}
