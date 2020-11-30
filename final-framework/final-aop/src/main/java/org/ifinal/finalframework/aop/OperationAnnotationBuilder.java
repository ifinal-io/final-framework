package org.ifinal.finalframework.aop;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * {@link Operation} 注解构建器，将符合条件的{@link Annotation} 转化为对应的 {@link Operation}。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationAnnotationBuilder {
    @NonNull
    default AnnotationAttributes build(@NonNull Class<?> type, @NonNull Annotation ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @NonNull
    default AnnotationAttributes build(@NonNull Method method, @NonNull Annotation ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @NonNull
    default AnnotationAttributes build(@NonNull Integer index, @NonNull Parameter parameter, @NonNull Type parameterType, @NonNull Annotation ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
