package org.finalframework.spring.aop;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:20:28
 * @since 1.0
 */
public interface OperationAnnotationParser<O extends Operation> {

    @Nullable
    Collection<O> parseOperationAnnotation(Class<?> type);

    @Nullable
    Collection<O> parseOperationAnnotation(Method method);
}
