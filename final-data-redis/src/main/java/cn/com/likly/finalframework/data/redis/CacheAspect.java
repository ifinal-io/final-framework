package cn.com.likly.finalframework.data.redis;

import cn.com.likly.finalframework.data.redis.annotation.CacheDel;
import cn.com.likly.finalframework.data.redis.annotation.CacheSet;
import cn.com.likly.finalframework.data.redis.annotation.HCacheDel;
import cn.com.likly.finalframework.data.redis.annotation.HCacheSet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 11:25
 * @since 1.0
 */
@Aspect
@Component
public class CacheAspect {


    @Around(value = "@annotation(cacheSet)")
    public Object cacheSet(ProceedingJoinPoint point, CacheSet cacheSet) throws Throwable {
        return point.proceed();
    }

    @Around(value = "@annotation(cacheDel)")
    public Object cacheDel(ProceedingJoinPoint point, CacheDel cacheDel) throws Throwable {
        return point.proceed();
    }

    @Around(value = "@annotation(hCacheSet)")
    public Object hCacheSet(ProceedingJoinPoint point, HCacheSet hCacheSet) throws Throwable {
        return point.proceed();
    }

    @Around(value = "@annotation(hCacheDel)")
    public Object hCacheDel(ProceedingJoinPoint point, HCacheDel hCacheDel) throws Throwable {
        return point.proceed();
    }
}
