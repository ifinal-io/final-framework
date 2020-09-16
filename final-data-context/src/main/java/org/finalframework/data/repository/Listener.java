package org.finalframework.data.repository;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-08 15:47:58
 * @since 1.0
 */
public interface Listener<PARAM, DATA> {

    default PARAM onInit() {
        return null;
    }

    default void onStart(PARAM param) {
    }

    boolean onListening(int index, PARAM param, DATA data);


    default void onFinish(PARAM param) {
    }


}
