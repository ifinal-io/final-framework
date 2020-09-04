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

package org.finalframework.data.repository;


import org.apache.ibatis.binding.MapperProxy;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.util.ProxyUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-06 15:35:00
 * @since 1.0
 */
public class RepositoryHolder {
    private static final Map<Object, Class<? extends Repository<?, ?>>> repositories = new HashMap<>();
    private static final Field mapperInterfaceField = ReflectionUtils.findField(MapperProxy.class, "mapperInterface");

    static {
        mapperInterfaceField.setAccessible(true);
    }


    private final Repository<?, ?> repository;
    private final Class<? extends Repository> repositoryClass;
    private final Class<?> id;
    private final Class<?> entity;

    private RepositoryHolder(Repository<?, ?> repository, Class<? extends Repository> repositoryClass, Class<?> id, Class<?> entity) {
        this.repository = repository;
        this.repositoryClass = repositoryClass;
        this.id = id;
        this.entity = entity;
    }

    @SuppressWarnings("unchecked")
    public static <ID extends Serializable, T extends IEntity<ID>> RepositoryHolder from(@NonNull Repository repository) {
        final Class<? extends Repository> repositoryClass = (Class<? extends Repository>) ProxyUtils.targetClass(repository);
        if (repositoryClass == null) return null;

        Type[] genericInterfaces = repositoryClass.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType && Repository.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                Class<ID> id = (Class<ID>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Class<T> entity = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
                return new RepositoryHolder(repository, repositoryClass, id, entity);

            }
        }
        return null;
    }

    public Repository<?, ?> getRepository() {
        return repository;
    }

    public Class<? extends Repository> getRepositoryClass() {
        return repositoryClass;
    }

    public Class<?> getId() {
        return id;
    }

    public Class<?> getEntity() {
        return entity;
    }
}

