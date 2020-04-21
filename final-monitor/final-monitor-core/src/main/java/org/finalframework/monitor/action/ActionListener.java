package org.finalframework.monitor.action;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:52:06
 * @see Action
 * @since 1.0
 */
@FunctionalInterface
public interface ActionListener {

    void handle(Action<?> action);

}
