package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.annotation.IQuery;
import org.ifinal.finalframework.data.query.Query;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.data.repository.Repository#select(String, Class, Collection, IQuery)
 * @since 1.0.0
 */
public interface SelectTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeSelect(String tableName, Class<?> view, Collection<ID> ids, Query query);

    void afterSelect(String tableName, Class<?> view, Collection<ID> ids, Query query, Collection<T> entities);

}
