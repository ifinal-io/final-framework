package org.finalframework.spring.aop;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:40:57
 * @since 1.0
 */
@FunctionalInterface
public interface OperationInvoker {

    Object invoke() throws OperationException;

}
