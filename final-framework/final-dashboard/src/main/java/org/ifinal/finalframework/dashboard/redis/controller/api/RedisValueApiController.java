package org.ifinal.finalframework.dashboard.redis.controller.api;

import javax.annotation.Resource;
import org.ifinal.finalframework.web.annotation.auth.Auth;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/value")
public class RedisValueApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping
    public String get(final String key) {

        return stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping
    public void set(final String key, final String value) {

        stringRedisTemplate.opsForValue().set(key, value);
    }

}
