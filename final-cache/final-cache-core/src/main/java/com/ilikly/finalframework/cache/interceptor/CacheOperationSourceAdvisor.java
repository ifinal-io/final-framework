package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheOperationSource;
import lombok.Setter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 13:19:00
 * @since 1.0
 */
public class CacheOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Setter
    private CacheOperationSource cacheOperationSource;

    private final CacheOperationSourcePointcut pointcut = new CacheOperationSourcePointcut() {
        @Override
        protected CacheOperationSource getCacheOperationSource() {
            return cacheOperationSource;
        }
    };

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
