package org.ifinal.finalframework.data.repository;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SelectListener<PARAM, T> extends Listener<PARAM, List<T>> {
    @Override
    boolean onListening(int index, PARAM param, List<T> list);
}
