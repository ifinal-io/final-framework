package org.ifinal.finalframework.aop;

import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationContext {

    AnnotationAttributes annotationAttributes();

    MethodMetadata metadata();

    default Class<? extends Annotation> annotationType() {
        return annotationAttributes().annotationType();
    }

    default CutPoint cutPoint() {
        return annotationAttributes().getEnum("cutPoint");
    }

    Object target();

    Object[] args();

    Class<?> view();

    Map<String, Object> attributes();

    void addAttribute(String name, Object value);

    <T> T getAttribute(String name);
}
