package org.finalframework.cache.interceptor;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheConfiguration;
import org.finalframework.cache.CacheOperationSource;
import org.finalframework.cache.RedisCache;
import org.finalframework.spring.coding.AutoConfiguration;
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
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSourceAdvisor cacheOperationSourceAdvisor() {
        final CacheOperationSourceAdvisor advisor = new CacheOperationSourceAdvisor();
        advisor.setCacheOperationSource(cacheOperationSource());
        advisor.setAdviceBeanName("cacheInterceptor");
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheConfiguration cacheConfiguration() {
        return new CacheConfiguration();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource cacheOperationSource() {
        return new AnnotationCacheOperationSource(cacheConfiguration());
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor() {
        final CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSource(cacheOperationSource());
        interceptor.setCacheConfiguration(cacheConfiguration());
        return interceptor;
    }

    @Bean
    public Cache redisCache() {
        return new RedisCache();
    }
}
