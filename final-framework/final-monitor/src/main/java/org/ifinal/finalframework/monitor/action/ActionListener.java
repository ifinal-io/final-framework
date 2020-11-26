package org.ifinal.finalframework.monitor.action;


import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @see Action
 * @since 1.0.0
 */
@FunctionalInterface
public interface ActionListener {

    void onAction(@NonNull Action<?> action);

}
