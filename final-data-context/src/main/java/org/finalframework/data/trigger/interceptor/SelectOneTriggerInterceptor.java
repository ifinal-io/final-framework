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

package org.finalframework.data.trigger.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.data.query.Query;
import org.finalframework.data.repository.Repository;
import org.finalframework.data.trigger.SelectTrigger;
import org.finalframework.data.trigger.TriggerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 22:24:27
 * @see Repository#select(String, Class, Collection, Query)
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class SelectOneTriggerInterceptor implements TriggerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SelectOneTriggerInterceptor.class);

    private final TriggerManager triggerManager;

    public SelectOneTriggerInterceptor(TriggerManager triggerManager) {
        this.triggerManager = triggerManager;
    }

    private static Class<?> findRepositoryEntity(Class<? extends Repository<?, ?>> repository) {
        Type[] genericInterfaces = repository.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType && Repository.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[1];
                return (Class<?>) typeArgument;
            }

        }
        return null;
    }

    @Override
    public void before(Class<? extends Repository<?, ?>> repository, Method method, Object[] args) {
        final Class<?> entity = findRepositoryEntity(repository);
        final List<SelectTrigger<?, ?>> selectTriggers = triggerManager.getSelectTriggers(entity);
        if (Assert.nonEmpty(selectTriggers)) {
            final String tableName = (String) args[0];
            final Class<?> view = (Class<?>) args[1];
            final Collection ids = Collections.singletonList(args[2]);
            final Query query = (Query) args[3];
            selectTriggers.forEach(it -> it.beforeSelect(tableName, view, ids, query));
        }

    }

    @Override
    public void after(Class<? extends Repository<?, ?>> repository, Method method, Object[] args, Object result) {
        final Class<?> entity = findRepositoryEntity(repository);
        final List<SelectTrigger<?, ?>> selectTriggers = triggerManager.getSelectTriggers(entity);
        if (Assert.nonEmpty(selectTriggers)) {
            final String tableName = (String) args[0];
            final Class<?> view = (Class<?>) args[1];
            final Collection ids = Collections.singletonList(args[2]);
            final Query query = (Query) args[3];
            final List<Object> entities = Collections.singletonList(result);
            selectTriggers.forEach(it -> it.afterSelect(tableName, view, ids, query, (Collection) entities));
        }
    }
}

