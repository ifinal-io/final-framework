/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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

