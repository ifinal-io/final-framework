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
