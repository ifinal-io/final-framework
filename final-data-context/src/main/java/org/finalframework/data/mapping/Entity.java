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

package org.finalframework.data.mapping;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.finalframework.annotation.data.NonCompare;
import org.finalframework.core.Assert;
import org.finalframework.core.Streamable;
import org.finalframework.data.serializer.EntityJsonSerializer;
import org.springframework.data.mapping.PersistentEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0PP
 * @date 2018-10-17 10:52
 * @since 1.0
 */
@JsonSerialize(using = EntityJsonSerializer.class)
public interface Entity<T> extends PersistentEntity<T, Property>, Streamable<Property>, Iterable<Property> {

    static <T> Entity<T> from(Class<T> entityClass) {
        Assert.isNull(entityClass, "entityClass must not be null!");
        return EntityCache.getInstance().get(entityClass);
    }

    @SuppressWarnings("unchecked")
    static <T> List<CompareProperty> compare(T before, T after) {
        Entity<T> entity = (Entity<T>) from(before.getClass());
        return entity.stream()
                .filter(it -> !it.isTransient() && !it.hasAnnotation(NonCompare.class))
                .map(property -> CompareProperty.builder()
                        .property(property)
                        .value(property.get(before), property.get(after))
                        .build())
                .collect(Collectors.toList());
    }

    default T getInstance() {
        try {
            return getType().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("the entity of %s must have no args constructor!", getType().getName()));
        }
    }

}
