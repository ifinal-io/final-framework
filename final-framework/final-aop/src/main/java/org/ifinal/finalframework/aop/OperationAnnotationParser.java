package org.ifinal.finalframework.aop;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Operation 注解解析器
 *
 * @author likly
 * @version 1.0.0
 * @see AnnotationBuilder
 * @since 1.0.0
 */
public interface OperationAnnotationParser {

    @Nullable
    Collection<AnnotationAttributes> parseOperationAnnotation(Class<?> type);

    @Nullable
    Collection<AnnotationAttributes> parseOperationAnnotation(Method method);
}
