package org.finalframework.core.listener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-05-05 10:07:31
 * @since 1.0
 */
public interface ListenerConfigurer<T> {

    void addListener(T listener);

}
