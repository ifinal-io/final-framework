package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.RedisCache;
import com.ilikly.finalframework.spring.coding.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:15:49
 * @since 1.0
 */
@AutoConfiguration
@SuppressWarnings("unused")
public class CacheInterceptorAutoConfiguration {
    @Bean
    public CacheOperationInterceptor cacheOperationInterceptor() {
        return new CacheOperationInterceptor();
    }

    @Bean
    public Cache redisCache() {
        return new RedisCache();
    }
}
