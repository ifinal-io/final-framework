package cn.com.likly.finalframework.redis.impl;

import cn.com.likly.finalframework.redis.RedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 14:04:43
 * @since 1.0
 */
@Service
@SuppressWarnings("unchecked")
@ConditionalOnMissingBean(RedisService.class)
public class DefaultRedisService implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> T get(String key, Class<T> cacheType) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Type cacheType) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }
}
