package org.finalframework.boot.autoconfigure.redis;

import org.finalframework.redis.FinalRedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import java.net.UnknownHostException;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/16 11:33:10
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
public class FinalRedisAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public FinalRedisTemplate finalRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        FinalRedisTemplate template = new FinalRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
