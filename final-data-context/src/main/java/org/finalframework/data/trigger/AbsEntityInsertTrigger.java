

package org.finalframework.data.trigger;

import org.finalframework.annotation.data.AbsEntity;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;

import java.util.Collection;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 23:47:00
 * @since 1.0
 */
@SpringComponent
public class AbsEntityInsertTrigger implements InsertTrigger<Long, AbsEntity> {

    @Override
    public void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities) {

    }

    @Override
    public void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<AbsEntity> entities, int rows) {

    }
}
