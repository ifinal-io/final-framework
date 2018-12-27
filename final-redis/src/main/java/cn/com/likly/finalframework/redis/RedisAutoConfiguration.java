package cn.com.likly.finalframework.redis;

import cn.com.likly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import cn.com.likly.finalframework.redis.impl.DefaultRedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 14:10:58
 * @since 1.0
 */
@AutoConfiguration
public class RedisAutoConfiguration {

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
    }

    @Bean
    public RedisService redisService() {
        return new DefaultRedisService();
    }
}
