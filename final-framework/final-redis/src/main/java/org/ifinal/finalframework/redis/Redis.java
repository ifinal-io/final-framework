package org.ifinal.finalframework.redis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public interface Redis {

    static RedisTemplate template() {
        return RedisRegistry.getInstance().template();
    }

    @NonNull
    static RedisOperations key() {
        return RedisRegistry.getInstance().key();
    }

    static ValueOperations value() {
        return RedisRegistry.getInstance().value();
    }

    static HashOperations hash() {
        return RedisRegistry.getInstance().hash();
    }

    static ListOperations list() {
        return RedisRegistry.getInstance().list();
    }

    static SetOperations set() {
        return RedisRegistry.getInstance().set();
    }

    static ZSetOperations zset() {
        return RedisRegistry.getInstance().zset();
    }

    static boolean lock(final Object key, final Object value, final Long timeout, final TimeUnit unit) {

        if (timeout != null && unit != null) {
            return Boolean.TRUE.equals(value().setIfAbsent(key, value, timeout, unit));
        } else {
            return Boolean.TRUE.equals(value().setIfAbsent(key, value));

        }

    }

    static boolean unlock(final Object key, final Object value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] "
            + "then return redis.call('del', KEYS[1]) else return 0 end";
        return Boolean.TRUE.equals(
            key().execute(new DefaultRedisScript<>(script, Boolean.class), Collections.singletonList(key), value));
    }

}
