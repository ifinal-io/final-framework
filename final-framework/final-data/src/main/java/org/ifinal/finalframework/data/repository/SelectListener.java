package org.ifinal.finalframework.data.repository;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SelectListener<P, T> extends Listener<P, List<T>> {
    @Override
    boolean onListening(int index, P param, List<T> list);
}
