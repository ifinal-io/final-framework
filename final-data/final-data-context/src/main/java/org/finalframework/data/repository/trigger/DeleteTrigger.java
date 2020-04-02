package org.finalframework.data.repository.trigger;

import java.io.Serializable;
import java.util.Collection;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:12:04
 * @see org.finalframework.data.repository.Repository#delete
 * @since 1.0
 */
public interface DeleteTrigger<ID extends Serializable, T extends IEntity<ID>> {

    void beforeDelete(Collection<ID> ids, Query query);

    /**
     * @see org.finalframework.data.repository.Repository#delete(String, Collection, Query)
     */
    void afterDelete(Collection<ID> ids, Query query, int rows);

}
