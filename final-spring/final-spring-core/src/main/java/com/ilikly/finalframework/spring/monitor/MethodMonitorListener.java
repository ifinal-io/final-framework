package com.ilikly.finalframework.spring.monitor;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-28 22:19:27
 * @since 1.0
 */
public interface MethodMonitorListener {
    default void onStart(MethodPoint point) {
    }

    default void onReturn(MethodPoint point, Object result, long duration) {
    }

    default void onException(MethodPoint point, Exception exception, long duration) {
    }

    default void onFinish(MethodPoint point, Object result, Exception exception, long duration) {
    }

}
