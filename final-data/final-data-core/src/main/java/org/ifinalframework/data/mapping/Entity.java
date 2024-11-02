/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.mapping;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mapping.PersistentEntity;

import org.ifinalframework.data.annotation.NonCompare;
import org.ifinalframework.data.util.TableUtils;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.util.stream.Streamable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Entity<T> extends PersistentEntity<T, Property>, Streamable<Property>, Iterable<Property> {

    /**
     * return the entity from the {@link Class clazz}
     *
     * @param entityClass entity class
     * @param <T>         entity type
     * @return entity
     */
    static <T> Entity<T> from(final Class<T> entityClass) {

        Asserts.requiredNonNull(entityClass, "entityClass must not be null!");
        return EntityCache.getInstance().get(entityClass);
    }

    @SuppressWarnings("unchecked")
    static <T> List<CompareProperty> compare(final T before, final T after) {

        Entity<T> entity = (Entity<T>) from(before.getClass());
        return entity.stream()
                .filter(it -> !it.isTransient() && !it.isAnnotationPresent(NonCompare.class))
                .map(property -> CompareProperty.builder()
                        .property(property)
                        .value(property.get(before), property.get(after))
                        .build())
                .collect(Collectors.toList());
    }

    default String getSimpleName() {
        return getType().getSimpleName();
    }

    default String getTable() {
        return TableUtils.getTable(getType());
    }

    /**
     * return an instance of this entity
     *
     * @return an instance
     */
    default T getInstance() {
        return BeanUtils.instantiateClass(getType());
    }

}
