package com.ilikly.finalframework.redis;


import com.ilikly.finalframework.core.Assert;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:53:01
 * @since 1.0
 */
public final class RedisRegistry {
    private static final RedisRegistry instance = new RedisRegistry();
    private RedisService redisService;

    private RedisRegistry() {
    }

    public static RedisRegistry getInstance() {
        return instance;
    }

    public RedisService getRedisService() {
        Assert.isNull(redisService, "redis service had not been registered.");
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
