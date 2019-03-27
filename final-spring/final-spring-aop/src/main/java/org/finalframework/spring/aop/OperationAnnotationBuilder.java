package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:41:14
 * @since 1.0
 */
public interface OperationAnnotationBuilder<A extends Annotation, O extends Operation> {
    @NonNull
    default O build(@NonNull Class<?> type, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @NonNull
    default O build(@NonNull Method method, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @NonNull
    default O build(@NonNull Integer index, @NonNull Parameter parameter, @NonNull Type parameterType, @NonNull A ann) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
