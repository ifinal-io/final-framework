package org.ifinal.finalframework.aop;

import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class OperationCacheKey<O extends Operation> implements Comparable<OperationCacheKey> {

    private final O operation;

    private final AnnotatedElementKey methodCacheKey;

    public OperationCacheKey(O operation, Method method, Class<?> targetClass) {
        this.operation = operation;
        this.methodCacheKey = new AnnotatedElementKey(method, targetClass);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OperationCacheKey)) {
            return false;
        }
        OperationCacheKey otherKey = (OperationCacheKey) other;
        return (this.operation.equals(otherKey.operation) &&
                this.methodCacheKey.equals(otherKey.methodCacheKey));
    }

    @Override
    public int hashCode() {
        return (this.operation.hashCode() * 31 + this.methodCacheKey.hashCode());
    }

    @Override
    public String toString() {
        return this.operation + " on " + this.methodCacheKey;
    }

    @Override
    public int compareTo(OperationCacheKey other) {
        int result = this.operation.getClass().getSimpleName().compareTo(other.operation.getClass().getSimpleName());
        if (result == 0) {
            result = this.methodCacheKey.compareTo(other.methodCacheKey);
        }
        return result;
    }
}
