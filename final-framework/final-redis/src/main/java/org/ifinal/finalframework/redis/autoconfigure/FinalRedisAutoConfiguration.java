package org.ifinal.finalframework.redis.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import org.ifinal.finalframework.redis.ObjectJsonRedisTemplate;
import org.ifinal.finalframework.redis.RedisRegistry;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
public class FinalRedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ObjectJsonRedisTemplate finalRedisTemplate(final RedisConnectionFactory redisConnectionFactory) {

        ObjectJsonRedisTemplate template = new ObjectJsonRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        RedisRegistry.getInstance().setRedisTemplate(template);
        return template;
    }

}
