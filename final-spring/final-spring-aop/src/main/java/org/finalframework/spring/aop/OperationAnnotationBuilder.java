package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * {@link Operation} 注解构建器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:41:14
 * @since 1.0
 */
public interface OperationAnnotationBuilder<A extends Annotation, O extends Operation> {
    /**
     * 将标记在 {@link Class}上的{@link A} 构建成相应的 {@link O}
     *
     * @param type Class
     * @param ann  A
     */
    @NonNull
    default O build(@NonNull Class<?> type, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    /**
     * 将标记在 {@link Method}上的{@link A} 构建成相应的 {@link O}
     *
     * @param method Method
     * @param ann    A
     */
    @NonNull
    default O build(@NonNull Method method, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    /**
     * 将标记在 {@link Parameter}上的{@link A} 构建成相应的 {@link O}
     *
     * @param index         index
     * @param parameter     Parameter
     * @param parameterType Type
     * @param ann           A
     */
    @NonNull
    default O build(@NonNull Integer index, @NonNull Parameter parameter, @NonNull Type parameterType, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
