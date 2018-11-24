package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.annotation.CacheDel;
import cn.com.likly.finalframework.cache.annotation.CacheSet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 23:34:19
 * @since 1.0
 */
@Aspect
@Component
@SuppressWarnings("unchecked")
public class CacheOperationInterceptor {

    private final CacheOperationAspect aspect = new CacheOperationAspect<>();

    @Around("@annotation(cacheSet)")
    public Object cacheSet(ProceedingJoinPoint point, CacheSet cacheSet) throws Throwable {
        return aspect.doAspect(point, cacheSet, CacheSet.class);
    }

    @Around("@annotation(cacheDel)")
    public Object cacheDel(ProceedingJoinPoint point, CacheDel cacheDel) throws Throwable {
        return aspect.doAspect(point, cacheDel, CacheDel.class);
    }
}
