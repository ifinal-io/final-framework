package org.finalframework.redis.api.controller;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.ScanCursor;
import org.finalframework.redis.Redis;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.*;

import java.io.Closeable;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-28 17:35:10
 * @since 1.0
 */
@SpringConfiguration
@RestController
@RequestMapping("/api/redis")
@SuppressWarnings("unchecked")
public class RedisKeyApiController {

    @GetMapping("/keys")
    public Collection<String> keys(@RequestParam(value = "key", required = false, defaultValue = "*") String key) {
        return Redis.key().keys(key);
    }

    @GetMapping("/scan")
    public Object scan(@RequestParam(value = "cursor", defaultValue = "0", required = false) Long cursor,
                       @RequestParam(value = "pattern") String pattern,
                       @RequestParam(value = "count", defaultValue = "1000", required = false) Long count) {
        Map<String, Object> result = new HashMap<>();
        final List<String> keys = new ArrayList<>();
        final ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        final ConvertingCursor<?, String> resultKeys = (ConvertingCursor<?, String>) Redis.key().executeWithStickyConnection(connection -> new ConvertingCursor<>(
                connection.scan(scanOptions), Redis.template().getKeySerializer()::deserialize));


//        result.put("keys", resultKeys.getKeys());
//        result.put("cursor", resultKeys.getCursor());

        return result;

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
