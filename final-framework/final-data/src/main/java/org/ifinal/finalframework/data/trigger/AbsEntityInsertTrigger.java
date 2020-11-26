package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author sli
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AbsEntityInsertTrigger implements InsertTrigger<Long, AbsEntity> {

    @Override
    public void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities) {

    }

    @Override
    public void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities, int rows) {

    }
}
