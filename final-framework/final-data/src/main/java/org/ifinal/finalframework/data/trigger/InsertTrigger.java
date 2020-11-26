package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.IEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0.0
 * @see org.ifinal.finalframework.data.repository.Repository#insert(String, Class, boolean, Collection)
 * @see org.ifinal.finalframework.data.repository.Repository#replace(String, Class, Collection)
 * @see org.ifinal.finalframework.data.repository.Repository#save(String, Class, Collection)
 * @since 1.0.0
 */
public interface InsertTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<T> entities);

    void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<T> entities, int rows);

}
