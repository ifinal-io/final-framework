package org.finalframework.data.trigger;

import org.finalframework.annotation.IEntity;
import org.finalframework.data.query.Query;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 13:49:20
 * @see org.finalframework.data.repository.Repository#select(String, Class, Collection, Query)
 * @since 1.0
 */
public interface SelectTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeSelect(String tableName, Class<?> view, Collection<ID> ids, Query query);

    void afterSelect(String tableName, Class<?> view, Collection<ID> ids, Query query, Collection<T> entities);

}
