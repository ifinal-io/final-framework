/*
 * Copyright 2020-2022 the original author or authors.
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

import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultEntityFactory.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
public class DefaultEntityFactory implements EntityFactory {
    private final Environment environment;

    private final Map<Class<?>, Entity<?>> cache = new ConcurrentHashMap<>(1024);

    public DefaultEntityFactory() {
        this(new StandardEnvironment());
    }

    public DefaultEntityFactory(Environment environment) {
        this.environment = environment;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> Entity<T> create(@NonNull Class<T> clazz) {
        return (Entity<T>) cache.computeIfAbsent(clazz, key -> {
            final AnnotationPersistentEntity<T> entity = new AnnotationPersistentEntity<>(clazz);

            try {
                final Class<?> entityClass = entity.getType();
                BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
                Arrays.stream(beanInfo.getPropertyDescriptors())
                        .filter(it -> !"class".equals(it.getName()))
                        .map(it -> buildProperty(entity, entityClass, it))
                        .forEach(entity::addPersistentProperty);

            } catch (IntrospectionException e) {
                logger.error("", e);
            }


            return entity;
        });
    }

    private Property buildProperty(AnnotationPersistentEntity<?> entity, final Class<?> entityClass, final PropertyDescriptor descriptor) {
        TypeInformation<?> typeInformation = entity.getTypeInformation();
        final Field field = ReflectionUtils.findField(entityClass, descriptor.getName());
        return field == null
                ? new AnnotationPersistentProperty(
                org.springframework.data.mapping.model.Property.of(typeInformation, descriptor), entity,
                SimpleTypeHolder.DEFAULT, environment)
                : new AnnotationPersistentProperty(
                org.springframework.data.mapping.model.Property.of(typeInformation, field, descriptor), entity,
                SimpleTypeHolder.DEFAULT, environment);
    }
}
