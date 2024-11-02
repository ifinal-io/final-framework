/*
 * Copyright 2020-2021 the original author or authors.
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

import org.springframework.lang.Nullable;

import org.ifinalframework.data.mapping.Property;
import org.ifinalframework.util.Asserts;

import org.apache.ibatis.type.TypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class QPropertyImpl<T, E extends QEntity<?, ?>> implements QProperty<T> {

    private final E entity;

    private final Property property;

    private final Integer order;

    private final String path;

    private final String name;

    private final String column;

    private final String insert;
    private final String update;

    private final boolean idProperty;

    private final boolean readable;

    private final boolean writeable;

    private final boolean modifiable;

    private Type genericType;

    private final Class<? extends TypeHandler> typeHandler;

    private final List<Class<?>> views;

    public QPropertyImpl(final Builder<T, E> builder) {

        this.entity = builder.entity;
        this.property = builder.property;
        this.order = builder.order;
        this.path = builder.path;
        this.name = builder.name;
        this.column = builder.column;
        this.insert = builder.insert;
        this.update = builder.update;

        this.idProperty = builder.idProperty;
        this.readable = builder.isReadable;
        this.writeable = builder.isWriteable;
        this.modifiable = builder.isModifiable;

        this.genericType = builder.genericType;
        this.typeHandler = builder.typeHandler;

        this.views = Asserts.isEmpty(builder.views) ? new ArrayList<>() : builder.views;

    }

    @Override
    public E getEntity() {
        return this.entity;
    }

    public Property getProperty() {
        return this.property;
    }

    @Override
    public Class<T> getType() {
        return null;
    }

    public Type getGenericType() {
        return genericType;
    }

    @Override
    public Integer getOrder() {
        return this.order;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getTable() {
        return this.entity.getTable();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Nullable
    @Override
    public String getInsert() {
        return insert;
    }

    @Override
    public String getUpdate() {
        return update;
    }

    @Nullable
    @Override
    public String getReader() {
        return null;
    }

    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public boolean isIdProperty() {
        return idProperty;
    }

    @Override
    public boolean isVersionProperty() {
        return property.isVersionProperty();
    }

    @Override
    public boolean isReadable() {
        return this.readable;
    }

    @Override
    public boolean isWriteable() {
        return this.writeable;
    }

    @Override
    public boolean isModifiable() {
        return this.modifiable;
    }

    @Override
    public Class<? extends TypeHandler> getTypeHandler() {
        return this.typeHandler;
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotation) {
        return property.isAnnotationPresent(annotation);
    }

    @Override
    public boolean isArray() {
        return property.isArray();
    }

    @Override
    public boolean hasView(final Class<?> view) {

        if (view == null || Asserts.isEmpty(views)) {
            return true;
        }
        for (Class<?> item : views) {
            if (item.isAssignableFrom(view) || item.equals(view)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unique() {
        return false;
    }

    @Override
    public boolean nonnull() {
        return false;
    }

    /**
     * Builder.
     *
     * @param <T> type.
     * @param <E> entity type.
     */
    public static class Builder<T, E extends QEntity> {

        private final E entity;

        private final Property property;

        private Integer order = 0;

        private String path;

        private String name;

        private String column;

        private String insert;
        private String update;

        private boolean idProperty = false;

        private boolean isReadable = true;

        private boolean isWriteable = true;

        private boolean isModifiable = true;
        private Type genericType;
        private Class<? extends TypeHandler> typeHandler;

        private List<Class<?>> views;

        public Builder(final E entity, final Property property) {

            this.entity = entity;
            this.property = property;
        }

        public Builder<T, E> order(final Integer order) {

            this.order = order;
            return this;
        }

        public Builder<T, E> path(final String path) {

            this.path = path;
            return this;
        }

        public Builder<T, E> name(final String name) {

            this.name = name;
            return this;
        }

        public Builder<T, E> column(final String column) {

            this.column = column;
            return this;
        }

        public Builder<T, E> insert(String insert) {
            this.insert = insert;
            return this;
        }

        public Builder<T, E> update(String update) {
            this.update = update;
            return this;
        }

        public Builder<T, E> idProperty(final boolean idProperty) {

            this.idProperty = idProperty;
            return this;
        }

        public Builder<T, E> readable(final boolean readable) {

            this.isReadable = readable;
            return this;
        }

        public Builder<T, E> writeable(final boolean writeable) {

            this.isWriteable = writeable;
            return this;
        }

        public Builder<T, E> modifiable(final boolean modifiable) {

            this.isModifiable = modifiable;
            return this;
        }

        public Builder<T, E> genericType(final Type genericType) {
            this.genericType = genericType;
            return this;
        }

        public Builder<T, E> typeHandler(final Class<? extends TypeHandler> typeHandler) {

            this.typeHandler = typeHandler;
            return this;
        }

        public Builder<T, E> views(final List<Class<?>> views) {

            this.views = views;
            return this;
        }

        public QProperty<T> build() {
            return new QPropertyImpl<>(this);
        }

    }

}
