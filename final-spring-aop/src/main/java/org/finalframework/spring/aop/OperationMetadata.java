

package org.finalframework.spring.aop;

import org.finalframework.spring.expression.MethodMetadata;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:45:21
 * @since 1.0
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
