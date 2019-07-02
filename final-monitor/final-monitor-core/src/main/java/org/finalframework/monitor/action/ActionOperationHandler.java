package org.finalframework.monitor.action;

import org.finalframework.spring.aop.OperationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:59:21
 * @since 1.0
 */
public interface ActionOperationHandler<T> extends OperationHandler<ActionRecorder<T>, ActionOperation> {

}
