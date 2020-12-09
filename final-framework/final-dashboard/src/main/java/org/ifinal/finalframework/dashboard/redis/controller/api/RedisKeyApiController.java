package org.ifinal.finalframework.dashboard.redis.controller.api;

import org.ifinal.finalframework.auth.annotation.Auth;
import org.ifinal.finalframework.redis.Redis;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/key")
public class RedisKeyApiController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @DeleteMapping
    public Boolean delete(@RequestParam("key") String key) {
        return stringRedisTemplate.delete(key);
    }


    @GetMapping
    public List<String> scan(String pattern) {

        ScanOptions options = ScanOptions.scanOptions().count(1).match(pattern).build();

        RedisTemplate<?, ?> redisTemplate = Redis.template();


        Cursor<String> cursor = (Cursor<String>) redisTemplate.executeWithStickyConnection(
                redisConnection -> new ConvertingCursor<>(redisConnection.scan(options),
                        redisTemplate.getKeySerializer()::deserialize));


        List<String> keys = new LinkedList<>();
        cursor.forEachRemaining(keys::add);
        return keys;


    }


}
