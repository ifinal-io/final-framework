package org.finalframework.data.repository;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-08 16:17:11
 * @since 1.0
 */
public interface DeleteListener<PARAM> extends Listener<PARAM, Integer> {

    @Override
    boolean onListening(int index, PARAM param, Integer rows);
}
