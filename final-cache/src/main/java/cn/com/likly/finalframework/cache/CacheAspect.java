package cn.com.likly.finalframework.cache;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:49:03
 * @since 1.0
 */
public interface CacheAspect<A extends Annotation> {

    Object doAspect(ProceedingJoinPoint point, A ann);
}
