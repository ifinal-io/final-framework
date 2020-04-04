package org.finalframework.test.dao.trigger;


import org.finalframework.data.query.Query;
import org.finalframework.data.trigger.SelectTrigger;
import org.finalframework.test.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 23:27:42
 * @since 1.0
 */
@Component
public class PersonSelectTrigger implements SelectTrigger<Long, Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonSelectTrigger.class);

    @Override
    public void beforeSelect(String tableName, Class<?> view, Collection<Long> ids, Query query) {
        logger.info("table={},view={},ids={},query={}", tableName, view, ids, query);

    }

    @Override
    public void afterSelect(String tableName, Class<?> view, Collection<Long> ids, Query query, Collection<Person> entities) {
        logger.info("table={},view={},ids={},query={},size={}", tableName, view, ids, query, entities.size());

    }
}

