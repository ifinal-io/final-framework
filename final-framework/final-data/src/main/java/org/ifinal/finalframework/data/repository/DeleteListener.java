package org.ifinal.finalframework.data.repository;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface DeleteListener<PARAM> extends Listener<PARAM, Integer> {

    @Override
    boolean onListening(int index, PARAM param, Integer rows);
}
