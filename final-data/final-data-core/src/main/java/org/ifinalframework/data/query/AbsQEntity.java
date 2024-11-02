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

package org.ifinalframework.data.query;

import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;

import org.ifinalframework.core.IRecord;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.Column;
import org.ifinalframework.data.annotation.Reference;
import org.ifinalframework.data.annotation.Tenant;
import org.ifinalframework.data.annotation.View;
import org.ifinalframework.data.mapping.Entity;
import org.ifinalframework.data.mapping.MappingUtils;
import org.ifinalframework.data.mapping.Property;
import org.ifinalframework.data.util.TableUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsQEntity<I extends Serializable, T> implements QEntity<I, T> {

    private final List<QProperty<?>> properties = new ArrayList<>();

    private final Map<String, QProperty<?>> pathProperties = new LinkedHashMap<>();

    private final Map<String, QProperty<?>> nameProperties = new LinkedHashMap<>();
    private final Map<String, QProperty<?>> columnProperties = new LinkedHashMap<>();

    private final Class<T> type;

    private final String table;

    private QProperty<?> idProperty;

    private QProperty<?> versionProperty;
    private QProperty<?> tenantProperty;

    public AbsQEntity(final Class<T> type) {
        this(type, TableUtils.getTable(type));
    }

    public AbsQEntity(final Class<T> type, final String table) {

        this.type = type;
        this.table = table;
        this.initProperties();
    }

    protected void initProperties() {
        Entity<T> entity = Entity.from(type);

        entity.stream()
                .filter(it -> !it.isTransient())
                .forEach(property -> {

                    final View view = property.findAnnotation(View.class);
                    final List<Class<?>> views = Optional.ofNullable(view).map(value -> Arrays.asList(value.value()))
                            .orElse(null);
                    final int order = property.getOrder();
                    if (property.isReference()) {

                        final Entity<?> referenceEntity = Entity.from(property.getType());

                        AtomicInteger index = new AtomicInteger();

                        Reference reference = property.getRequiredAnnotation(Reference.class);

                        Map<String, Column> columns = Arrays.stream(reference.columns())
                                .collect(Collectors.toMap(Column::name, Function.identity()));

                        property.getReferenceProperties()
                                .forEach(referenceProperty -> {
                                    Property persistentProperty = referenceEntity.getRequiredPersistentProperty(referenceProperty);
                                    Type genericType = persistentProperty.getGenericType();
                                    if (IRecord.class.isAssignableFrom(type)
                                            && IUser.class.isAssignableFrom(property.getType())
                                            && "id".equals(referenceProperty)) {
                                        genericType = ResolvableType.forClass(type).as(IRecord.class).getGeneric(1)
                                                .as(IUser.class).resolveGeneric(0);
                                    }


                                    final Column column = columns.get(referenceProperty);
                                    addProperty(
                                            new QPropertyImpl.Builder<>(this, persistentProperty)
                                                    .order(order + index.getAndIncrement())
                                                    .path(property.getName() + "." + persistentProperty.getName())
                                                    .name(MappingUtils.formatPropertyName(property, persistentProperty))
                                                    .column(MappingUtils.formatColumn(entity, property, persistentProperty))
                                                    .insert(column == null
                                                            ? persistentProperty.getInsert() : String.join("", column.insert()))
                                                    .update(column == null
                                                            ? persistentProperty.getUpdate() : String.join("", column.update()))
                                                    .views(views)
                                                    .readable(true)
                                                    .writeable(property.isWriteable())
                                                    .modifiable(property.isModifiable())
                                                    .genericType(genericType)
                                                    .typeHandler(TypeHandlers.findTypeHandler(persistentProperty))
                                                    .build()
                                    );
                                });

                    } else {

                        addProperty(
                                new QPropertyImpl.Builder<>(this, property)
                                        .order(order)
                                        .path(property.getName())
                                        .name(property.getName())
                                        .column(MappingUtils.formatColumn(entity, property, null))
                                        .insert(property.getInsert())
                                        .update(property.getUpdate())
                                        .idProperty(property.isIdProperty())
                                        .readable(!property.isTransient() && !property.isVirtual() && !property.isWriteOnly())
                                        .writeable(property.isWriteable())
                                        .modifiable(property.isModifiable())
                                        .genericType(property.getGenericType())
                                        .typeHandler(TypeHandlers.findTypeHandler(property))
                                        .views(views)
                                        .build()
                        );
                    }
                });
        this.properties.sort(Comparator.comparing(QProperty::getOrder));
    }

    private void addProperty(final QProperty<?> property) {

        this.properties.add(property);
        this.pathProperties.put(property.getPath(), property);
        this.columnProperties.put(property.getColumn(), property);
        this.nameProperties.put(property.getName(), property);
        if (property.isIdProperty()) {
            this.idProperty = property;
        } else if (property.isVersionProperty()) {
            this.versionProperty = property;
        } else if (property.isAnnotationPresent(Tenant.class)) {
            this.tenantProperty = property;
        }
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public QProperty<I> getIdProperty() {
        return (QProperty<I>) this.idProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E> getVersionProperty() {
        return (QProperty<E>) this.versionProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E> getTenantProperty() {
        return (QProperty<E>) this.tenantProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E> getProperty(final String path) {
        QProperty<?> property = pathProperties.get(path);
        if (Objects.isNull(property)) {
            property = columnProperties.get(path);
        }
        if (Objects.isNull(property)) {
            property = nameProperties.get(path);
        }
        return (QProperty<E>) property;
    }

    @Override
    @NonNull
    public Iterator<QProperty<?>> iterator() {
        return properties.iterator();
    }

    public Stream<QProperty<?>> stream() {
        return properties.stream();
    }

}
