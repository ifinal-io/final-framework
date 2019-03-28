package org.finalframework.cache.interceptor;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheConfiguration;
import org.finalframework.cache.RedisCache;
import org.finalframework.spring.aop.interceptor.OperationSourceAdvisor;
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
    public OperationSourceAdvisor cacheOperationSourceAdvisor() {
        final OperationSourceAdvisor advisor = new OperationSourceAdvisor(cacheConfiguration());
        advisor.setAdviceBeanName("cacheInterceptor");
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheConfiguration cacheConfiguration() {
        final CacheConfiguration configuration = new CacheConfiguration();
        configuration.setExecutor(redisCache());
        return configuration;
    }

    @Bean
    public CacheInterceptor cacheInterceptor() {
        return new CacheInterceptor(cacheConfiguration());
    }

    @Bean
    public Cache redisCache() {
        return new RedisCache();
    }
}
