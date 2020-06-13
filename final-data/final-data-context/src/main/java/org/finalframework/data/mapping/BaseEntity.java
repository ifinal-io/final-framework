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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:06
 * @since 1.0
 */
public class BaseEntity<T> extends BasicPersistentEntity<T, Property> implements Entity<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    private final List<Property> properties = new ArrayList<>();
    private final Set<Class<?>> views = new LinkedHashSet<>();

    private BaseEntity(TypeInformation<T> information) {
        super(information);
    }

    public BaseEntity(Class<T> entityClass) {
        this(ClassTypeInformation.from(entityClass));
        init();
    }

    private void init() {
        initProperties();
    }

    @SuppressWarnings("unchecked")
    private void initProperties() {
        try {
            final Class entityClass = getType();
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
            Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(it -> !it.getName().equals("class"))
                    .map(it -> buildProperty(entityClass, it))
                    .forEach(it -> {
                        addPersistentProperty(it);
                        properties.add(it);
                    });


        } catch (IntrospectionException e) {
            logger.error("", e);
        }
    }

    private Property buildProperty(Class entityClass, PropertyDescriptor descriptor) {
        final Field field = getField(descriptor.getName(), entityClass);
        return field == null
                ? new AnnotationProperty(org.springframework.data.mapping.model.Property.of(getTypeInformation(), descriptor), this, SimpleTypeHolder.DEFAULT)
                : new AnnotationProperty(org.springframework.data.mapping.model.Property.of(getTypeInformation(), field, descriptor), this, SimpleTypeHolder.DEFAULT);
    }

    private Field getField(String name, Class target) {
        if (target == null || target == Object.class)
            return null;
        try {
            return target.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(name, target.getSuperclass());
        }
    }


    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }

}
