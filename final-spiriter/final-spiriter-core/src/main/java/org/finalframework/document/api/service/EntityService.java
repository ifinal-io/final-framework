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

package org.finalframework.document.api.service;

import java.util.List;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.entity.EntityHolder;
import org.finalframework.document.api.service.query.EntityQuery;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:13:36
 * @since 1.0
 */
public interface EntityService {

    /**
     * return the {@link Class} which is a {@link IEntity}
     *
     * @param query the entity query
     * @return the {@link Class} which is a {@link IEntity}
     */
    List<EntityHolder> query(EntityQuery query);

    /**
     * return the {@link Entity} of the {@code entity}.
     *
     * @param entity the {@link Class<?>} of entity
     */
    Entity<?> entity(Class<?> entity);
}
