package cn.com.likly.finalframework.redis.impl;

import cn.com.likly.finalframework.redis.RedisRegistry;
import cn.com.likly.finalframework.redis.RedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 14:04:43
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Configuration
//@ConditionalOnMissingBean(RedisService.class)
public class DefaultRedisService implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        RedisRegistry.getInstance().setRedisService(this);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key.toString(), value);
    }

    @Override
    public void set(Object key, Object value, long ttl, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key.toString(), value, ttl, timeUnit);
    }

    @Override
    public <T> T get(Object key) {
        return (T) redisTemplate.opsForValue().get(key.toString());
    }

    @Override
    public void hset(Object key, Object field, Object value) {
        redisTemplate.opsForHash().put(key.toString(), field.toString(), value);
    }

    @Override
    public <T> T hget(Object key, Object field) {
        return (T) redisTemplate.opsForHash().get(key.toString(), field.toString());
    }

    @Override
    public Boolean expire(Object key, long ttl, TimeUnit timeUnit) {
        return redisTemplate.expire(key.toString(), ttl, timeUnit);
    }


}
