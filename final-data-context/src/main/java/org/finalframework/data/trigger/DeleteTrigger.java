

package org.finalframework.data.trigger;

import org.finalframework.annotation.IEntity;
import org.finalframework.data.query.Query;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:12:04
 * @see org.finalframework.data.repository.Repository#delete(String, Collection, Query)
 * @since 1.0
 */
public interface DeleteTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeDelete(String tableName, Collection<ID> ids, Query query);

    /**
     * @see org.finalframework.data.repository.Repository#delete(String, Collection, Query)
     */
    void afterDelete(String tableName, Collection<ID> ids, Query query, int rows);

}
