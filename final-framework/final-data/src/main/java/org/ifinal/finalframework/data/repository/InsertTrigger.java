package org.ifinal.finalframework.data.repository;

import org.ifinal.finalframework.core.IEntity;

import java.util.Collection;

/**
 * InsertTrigger.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface InsertTrigger<T extends IEntity<?>> {

    default Collection<T> beforeInsert(Collection<T> entities) {
        return entities;
    }

    default void afterInsert(Collection<T> entities, int rows) {

    }

}
