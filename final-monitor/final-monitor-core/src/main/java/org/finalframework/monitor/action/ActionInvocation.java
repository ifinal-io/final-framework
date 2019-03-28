package org.finalframework.monitor.action;

import org.finalframework.spring.aop.Invocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:59:21
 * @since 1.0
 */
public interface ActionInvocation<T> extends Invocation<ActionRecorder<T>, ActionOperation> {

}
