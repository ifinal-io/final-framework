package org.ifinal.finalframework.data.repository;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface DeleteListener<P> extends Listener<P, Integer> {

    @Override
    boolean onListening(int index, P param, Integer rows);

}
