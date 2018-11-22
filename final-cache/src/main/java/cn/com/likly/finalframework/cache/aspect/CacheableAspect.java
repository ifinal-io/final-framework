package cn.com.likly.finalframework.cache.aspect;

import cn.com.likly.finalframework.cache.CacheAspect;
import cn.com.likly.finalframework.cache.annotation.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:50:44
 * @since 1.0
 */
public class CacheableAspect implements CacheAspect<Cacheable> {
    @Override
    public Object doAspect(ProceedingJoinPoint point, Cacheable ann) {
        return null;
    }
}
