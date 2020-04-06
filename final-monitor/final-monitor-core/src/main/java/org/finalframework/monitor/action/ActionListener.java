package org.finalframework.monitor.action;

import org.finalframework.monitor.OperatorContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:52:06
 * @see Action
 * @see OperatorContext
 * @since 1.0
 */
public interface ActionListener<T> {

    void handle(Action<T> context);

}
