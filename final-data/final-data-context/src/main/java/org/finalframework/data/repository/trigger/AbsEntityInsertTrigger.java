package org.finalframework.data.repository.trigger;

import java.util.Collection;
import org.finalframework.data.entity.AbsEntity;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 23:47:00
 * @since 1.0
 */
public class AbsEntityInsertTrigger implements InsertTrigger<Long, AbsEntity> {

    @Override
    public void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities) {

    }

    @Override
    public void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities, int rows) {

    }
}
