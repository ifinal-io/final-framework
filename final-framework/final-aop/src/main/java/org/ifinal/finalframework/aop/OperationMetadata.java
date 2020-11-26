package org.ifinal.finalframework.aop;

import org.ifinal.finalframework.context.expression.MethodMetadata;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class OperationMetadata<O extends Operation> extends MethodMetadata {

    private final O operation;

    public OperationMetadata(O operation, Method method, Class<?> targetClass) {
        super(method, targetClass);
        this.operation = operation;
    }

    public O getOperation() {
        return operation;
    }
}
