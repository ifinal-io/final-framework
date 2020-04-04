package org.finalframework.test.dao.trigger;


import org.finalframework.data.trigger.InsertTrigger;
import org.finalframework.test.entity.Person;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 13:35:18
 * @since 1.0
 */
@Component
public class PersonInsertTrigger implements InsertTrigger<Long, Person> {
    @Override
    public void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<Person> entities) {

    }

    @Override
    public void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<Person> entities, int rows) {

    }
}

