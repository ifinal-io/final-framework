package org.finalframework.data.repository.trigger;

import java.io.Serializable;
import java.util.Collection;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:12:04
 * @since 1.0
 */
public interface UpdateTrigger<ID extends Serializable, T extends IEntity<ID>> {

    void beforeUpdate(T entity, Update update, Collection<ID> ids, Query query);

    void afterUpdate(T entity, Update update, Collection<ID> ids, Query query);

}
