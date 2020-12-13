package org.ifinal.finalframework.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class AnnotationSourceMethodPoint extends StaticMethodMatcherPointcut {

    private final AnnotationSource<?> source;

    public AnnotationSourceMethodPoint(final AnnotationSource<?> source) {

        this.source = source;
    }

    @Override
    public boolean matches(final @NonNull Method method, final @NonNull Class<?> targetClass) {

        Object annotations = source.getAnnotations(method, targetClass);

        boolean matches = Asserts.nonEmpty(annotations) && !Boolean.FALSE.equals(annotations);
        if (matches) {
            logger.info("matched -> targetClass={},method={},annotations={}", targetClass, method, annotations);
        }
        return matches;
    }

}
