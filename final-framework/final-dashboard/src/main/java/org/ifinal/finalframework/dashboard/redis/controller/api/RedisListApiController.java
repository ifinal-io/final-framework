package org.ifinal.finalframework.dashboard.redis.controller.api;

import org.ifinal.finalframework.annotation.auth.Auth;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/list")
public class RedisListApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/lpop")
    public String lpop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }


    @PostMapping("/lpush")
    public Long lpush(String key, @RequestParam("value") List<String> value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    @GetMapping("/rpop")
    public String rpop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }


    @PostMapping("/rpush")
    public Long rpush(String key, @RequestParam("value") List<String> value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

}
