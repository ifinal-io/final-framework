package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.annotation.cache.Cache;
import org.ifinal.finalframework.aop.JoinPointInterceptorHandler;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.Nullable;

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
