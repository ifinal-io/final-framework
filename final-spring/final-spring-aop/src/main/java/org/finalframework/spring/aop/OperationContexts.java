package org.finalframework.spring.aop;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:06:57
 * @since 1.0
 */
public interface OperationContexts {

    OperationConfiguration configuration();

    <O extends Operation> Collection<OperationContext<? extends Operation>> get(Class<? extends O> operation);

}
