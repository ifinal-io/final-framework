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

package org.finalframework.document.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.finalframework.core.Asserts;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.entity.EntityHolder;
import org.finalframework.document.api.service.EntityService;
import org.finalframework.document.api.service.query.EntityQuery;
import org.finalframework.util.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:15:50
 * @since 1.0
 */
@Service
public class EntityServiceImpl implements EntityService {
    public static final Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);
    private final Map<String, Class<?>> entitiesMap = SpringFactoriesLoader.loadFactoryNames(IEntity.class, getClass().getClassLoader())
            .stream()
            .collect(Collectors.toMap(Function.identity(), Classes::forName));

    @Override
    public List<EntityHolder> query(EntityQuery query) {

        return entitiesMap.values()
                .stream()
                .filter(entity -> {
                    if (Asserts.nonEmpty(query.getName())) {
                        return entity.getCanonicalName().toUpperCase().contains(query.getName().toUpperCase());
                    } else {
                        return true;
                    }


                })
                .map(entity -> {
                    final EntityHolder holder = new EntityHolder();
                    holder.setName(entity.getSimpleName());
                    holder.setEntity(entity);
                    return holder;
                })
                .collect(Collectors.toList());


    }

    @Override
    public Entity<?> entity(Class<?> entity) {
        return Entity.from(entity);
    }
}

