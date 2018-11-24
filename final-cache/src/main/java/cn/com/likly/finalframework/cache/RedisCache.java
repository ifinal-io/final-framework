package cn.com.likly.finalframework.cache;

import cn.com.likly.finalframework.redis.Redis;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 23:56:03
 * @since 1.0
 */
@Component
public class RedisCache implements Cache {

    @PostConstruct
    public void init() {
        CacheRegistry.getInstance().registerCache(null, this);
    }

    @Override
    public void set(Object key, Object value, long ttl, TimeUnit timeUnit) {
        if (ttl > 0) {
            Redis.set(key, value, ttl, timeUnit);
        } else {
            Redis.set(key, value);
        }
    }

    @Override
    public <T> T get(Object key) {
        return Redis.get(key);
    }

    @Override
    public boolean del(Object key) {
        return Redis.del(key) == 1;
    }

    @Override
    public void hset(Object key, Object field, Object value, long ttl, TimeUnit timeUnit) {
        Redis.hset(key, field, value);
        if (ttl > 0) {
            Redis.expire(key, ttl, timeUnit);
        }
    }

    @Override
    public <T> T hget(Object key, Object field) {
        return Redis.hget(key, field);
    }

    @Override
    public boolean hdel(Object key, Object field) {
        return Redis.hdel(key, field) == 0;
    }


}
