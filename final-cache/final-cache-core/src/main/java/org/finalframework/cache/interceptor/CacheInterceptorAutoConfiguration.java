package org.finalframework.cache.interceptor;

import org.finalframework.cache.RedisCache;
import org.finalframework.cache.component.*;
import org.finalframework.coding.spring.AutoConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:15:49
 * @since 1.0
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfiguration
@SuppressWarnings("unused")
public class CacheInterceptorAutoConfiguration {

    @Bean
    public CacheValueComponent cacheValueComponent() {
        return new CacheValueComponent();
    }

    @Bean
    public CacheDelComponent cacheDelComponent() {
        return new CacheDelComponent();
    }

    @Bean
    public CacheIncrementComponent cacheIncrementComponent() {
        return new CacheIncrementComponent();
    }

    @Bean
    public CacheableComponent cacheableComponent() {
        return new CacheableComponent();
    }

    @Bean
    public CacheLockComponent cacheLockComponent() {
        return new CacheLockComponent();
    }

    @Bean
    public CachePutComponent cachePutComponent() {
        return new CachePutComponent();
    }

    @Bean
    public RedisCache redisCache() {
        return new RedisCache();
    }
}
