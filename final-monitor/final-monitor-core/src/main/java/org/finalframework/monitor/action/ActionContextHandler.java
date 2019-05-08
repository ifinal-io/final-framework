package org.finalframework.monitor.action;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:52:06
 * @see ActionContext
 * @see OperatorContext
 * @since 1.0
 */
public interface ActionContextHandler<T> {

    void handle(ActionContext<T> context);

}
