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

import java.io.Serializable;
import java.util.Collection;

import org.finalframework.data.annotation.IEntity;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:07:44
 * @see org.finalframework.data.repository.Repository#insert(String, Class, boolean, Collection)
 * @see org.finalframework.data.repository.Repository#replace(String, Class, Collection)
 * @see org.finalframework.data.repository.Repository#save(String, Class, Collection)
 * @since 1.0
 */
public interface InsertTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeInsert(String tableName, Class<?> view, boolean ignore, Collection<T> entities);

    void afterInsert(String tableName, Class<?> view, boolean ignore, Collection<T> entities, int rows);

}
