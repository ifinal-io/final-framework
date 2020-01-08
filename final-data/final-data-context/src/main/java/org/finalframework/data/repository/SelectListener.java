package org.finalframework.data.repository;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-08 16:20:30
 * @since 1.0
 */
public interface SelectListener<PARAM, T> extends Listener<PARAM, List<T>> {
    @Override
    boolean onListening(int index, PARAM param, List<T> list);
}
