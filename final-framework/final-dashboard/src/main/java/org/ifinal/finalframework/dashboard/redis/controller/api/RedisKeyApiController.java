package org.ifinal.finalframework.dashboard.redis.controller.api;

import org.ifinal.finalframework.annotation.auth.Auth;
import org.ifinal.finalframework.redis.Redis;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Boolean delete(final @RequestParam("key") String key) {

        return stringRedisTemplate.delete(key);
    }


    @GetMapping
    public List<String> scan(final String pattern) {

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
