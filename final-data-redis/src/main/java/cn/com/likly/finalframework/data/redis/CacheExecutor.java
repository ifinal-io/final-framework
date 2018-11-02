package cn.com.likly.finalframework.data.redis;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 11:37
 * @since 1.0
 */
public interface CacheExecutor<A extends Annotation> {
    Object execute(ProceedingJoinPoint point, A annotation) throws Throwable;
}
