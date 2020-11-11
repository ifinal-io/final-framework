package org.finalframework.monitor.action;


import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:52:06
 * @see Action
 * @since 1.0
 */
@FunctionalInterface
public interface ActionListener {

    void onAction(@NonNull Action<?> action);

}
