package org.ifinal.finalframework.aop;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class AnnotationSourceMethodPoint extends StaticMethodMatcherPointcut {

    private final AnnotationSource<?> source;

    public AnnotationSourceMethodPoint(AnnotationSource<?> source) {
        this.source = source;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Object annotations = source.getAnnotations(method, targetClass);

        boolean matches = Asserts.nonEmpty(annotations) && !Boolean.FALSE.equals(annotations);
        if (matches) {
            logger.info("matched -> targetClass={},method={},annotaions={}", targetClass, method, annotations);
        }
        return matches;
    }
}
