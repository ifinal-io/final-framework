package org.finalframework.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-06 00:35:52
 * @since 1.0
 */
public interface OperationInvocation extends MethodInvocation {

    Collection<OperationContext<Operation>> getOperationContexts();

}
