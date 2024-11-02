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

import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationPersistentEntity<T> extends BasicPersistentEntity<T, Property> implements Entity<T> {

    private final List<Property> properties = new ArrayList<>();

    private AnnotationPersistentEntity(final TypeInformation<T> information) {
        super(information);
    }

    public AnnotationPersistentEntity(final Class<T> entityClass) {
        this(TypeInformation.of(entityClass));
    }

    @Override
    public void addPersistentProperty(Property property) {
        super.addPersistentProperty(property);
        properties.add(property);
    }

    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }

}
