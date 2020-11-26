package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.Update;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UpdateTrigger<ID extends Serializable, T extends IEntity<ID>> {

    void beforeUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query);

    void afterUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query, int rows);

}
