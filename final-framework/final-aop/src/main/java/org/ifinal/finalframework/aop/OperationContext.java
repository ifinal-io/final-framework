package org.ifinal.finalframework.aop;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationContext<O extends Operation> {
    O operation();

    OperationMetadata<O> metadata();

    Object target();

    Object[] args();

    Class<?> view();

    void addAttribute(String name, Object value);

    <T> T getAttribute(String name);
}
