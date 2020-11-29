package org.ifinal.finalframework.data.repository;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Listener<P, T> {

    default P onInit() {
        return null;
    }

    default void onStart(P param) {
    }

    boolean onListening(int index, P param, T data);


    default void onFinish(P param) {
    }


}
