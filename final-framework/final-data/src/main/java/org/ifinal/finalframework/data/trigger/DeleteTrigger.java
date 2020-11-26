package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.annotation.IQuery;
import org.ifinal.finalframework.data.query.Query;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0.0
 * @see org.ifinal.finalframework.data.repository.Repository#delete(String, Collection, IQuery)
 * @since 1.0.0
 */
public interface DeleteTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeDelete(String tableName, Collection<ID> ids, Query query);

    /**
     * @param tableName tableName
     * @param ids       ids
     * @param query     query
     * @param rows      rows
     * @see org.ifinal.finalframework.data.repository.Repository#delete(String, Collection, IQuery)
     */
    void afterDelete(String tableName, Collection<ID> ids, Query query, int rows);

}
