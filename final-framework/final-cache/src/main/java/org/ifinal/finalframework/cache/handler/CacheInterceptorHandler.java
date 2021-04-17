package org.ifinal.finalframework.cache.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.aop.JoinPointInterceptorHandler;
import org.ifinal.finalframework.cache.annotation.Cache;
import org.ifinal.finalframework.core.aop.JoinPoint;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheInterceptorHandler extends JoinPointInterceptorHandler<Cache, AnnotationAttributes> {

    @Nullable
    default JoinPoint point(final AnnotationAttributes annotation) {

        return annotation.containsKey("point") ? annotation.getEnum("point") : null;
    }

}
