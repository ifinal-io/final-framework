package org.finalframework.aop;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Operation 注解解析器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:20:28
 * @see OperationAnnotationBuilder
 * @since 1.0
 */
public interface OperationAnnotationParser {

    /**
     * 解析标记在{@link Class}上的 {@link java.lang.annotation.Annotation},并将其构建成对应的{@link Operation}
     */
    @Nullable
    Collection<? extends Operation> parseOperationAnnotation(Class<?> type);

    /**
     * 解析标记在 {@link Method} 上的 {@link java.lang.annotation.Annotation},并将其构建成对应的{@link Operation}
     */
    @Nullable
    Collection<? extends Operation> parseOperationAnnotation(Method method);
}
