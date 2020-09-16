package org.finalframework.spiriter.redis.api.controller;

import org.finalframework.redis.Redis;
import org.finalframework.spiriter.redis.api.model.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.*;

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


    /**
     * <a href="http://doc.redisfans.com/key/del.html">DEL</a>
     *
     * @param keys 删除给定的一个或多个 key 。
     * @return 被删除 key 的数量。
     */
    @DeleteMapping
    public Long delete(Collection<String> keys) {
        final Long count = Redis.key().delete(keys);
        logger.debug("redis delete keys: {},count={}", keys, count);
        return count;
    }

    /**
     * <a href="http://doc.redisfans.com/key/exists.html>EXISTS</a>"
     *
     * @param key 检查给定 key 是否存在。
     * @return 若 key 存在，返回 {@code true} ，否则返回 {@code false} 。
     */
    @GetMapping("/exists")
    public Boolean exists(@RequestParam("key") String key) {
        return Redis.key().hasKey(key);
    }

    /**
     * <a href="http://doc.redisfans.com/key/expire.html>EXPIRE</a>
     * <a href="http://doc.redisfans.com/key/pexpire.html>PEXPIRE</a>
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    @PostMapping("expire")
    public Boolean expire(String key, Long timeout, @RequestParam(value = "unit", required = false, defaultValue = "MILLISECONDS") TimeUnit unit) {
        return Redis.key().expire(key, timeout, unit);
    }

    /**
     * <a href="http://doc.redisfans.com/key/expireat.html">EXPIREAT</a>
     *
     * @param key
     * @param timestamp
     * @return
     */
    public Boolean expireAt(String key, Date timestamp) {
        return Redis.key().expireAt(key, timestamp);
    }

    /**
     * <a href="http://doc.redisfans.com/key/keys.html">KEYS</a>
     *
     * @param pattern
     * @return
     */
    @GetMapping("/keys")
    public List<RedisKey> keys(@RequestParam("pattern") String pattern) {
        final Set<?> keys = Optional.ofNullable(Redis.key().keys(pattern)).orElse(Collections.EMPTY_SET);
        return keys.stream()
                .map(Object::toString)
                .map(key -> new RedisKey(key, type(key), ttl(key, TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS))
                .collect(Collectors.toList());
    }

    /**
     * <a href="http://doc.redisfans.com/key/persist.html">PERSIST</a>
     *
     * @param key
     * @return
     */
    public Boolean persist(String key) {
        return Redis.key().persist(key);
    }


    /**
     * <a href="http://doc.redisfans.com/key/ttl.html">TTL</a>
     *
     * @param key
     * @param unit
     * @return
     */
    @GetMapping("ttl")
    public Long ttl(@RequestParam("key") String key, @RequestParam(value = "unit", required = false, defaultValue = "MILLISECONDS") TimeUnit unit) {
        return Redis.key().getExpire(key, unit);
    }

    /**
     * <a href="http://doc.redisfans.com/key/type.html">TYPE</a>
     *
     * @param key
     * @return
     * @see DataType
     */
    @GetMapping("type")
    public DataType type(@RequestParam("key") String key) {
        return Redis.key().type(key);
    }


}

