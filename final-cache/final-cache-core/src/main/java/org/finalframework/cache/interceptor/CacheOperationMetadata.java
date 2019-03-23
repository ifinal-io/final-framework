package org.finalframework.cache.interceptor;

import lombok.Getter;
import org.finalframework.cache.CacheOperation;
import org.finalframework.spring.expression.MethodMetadata;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:05:09
 * @since 1.0
 */
@Getter
public class CacheOperationMetadata<O extends CacheOperation> extends MethodMetadata {

    private final O operation;

    public CacheOperationMetadata(O operation, Method method, Class<?> targetClass) {
        super(method, targetClass);
        this.operation = operation;
    }

}