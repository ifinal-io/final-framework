package cn.com.likly.finalframework.redis.impl;

import cn.com.likly.finalframework.redis.RedisRegistry;
import cn.com.likly.finalframework.redis.RedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 14:04:43
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Configuration
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
    public long del(Object... keys) {
        Long result = redisTemplate.delete(Arrays.stream(keys).map(Object::toString).collect(Collectors.toSet()));
        return result == null ? 0 : result;
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
    public Map<Object, Object> hgetAll(Object key) {
        return null;
    }

    @Override
    public boolean expire(Object key, long ttl, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key.toString(), ttl, timeUnit));
    }

    @Override
    public long hdel(Object key, Object... fields) {
        return redisTemplate.opsForHash().delete(Arrays.stream(fields).map(Object::toString).toArray());
    }


}
