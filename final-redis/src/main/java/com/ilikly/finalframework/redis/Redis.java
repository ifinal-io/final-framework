package com.ilikly.finalframework.redis;

import org.springframework.data.redis.core.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:52:35
 * @since 1.0
 */
public interface Redis {

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

}
