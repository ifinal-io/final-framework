package org.finalframework.data.trigger;

import org.finalframework.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:12:04
 * @since 1.0
 */
public interface UpdateTrigger<ID extends Serializable, T extends IEntity<ID>> {

    void beforeUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query);

    void afterUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query, int rows);

}
