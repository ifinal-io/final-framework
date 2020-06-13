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

package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.mapping.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-21 10:27:10
 * @since 1.0
 */
public class QPropertyImpl<T, E extends QEntity<?, ?>> implements QProperty<T> {

    private final E entity;
    private final Property property;
    private final String path;
    private final String name;
    private final String column;

    private final boolean idProperty;
    private final boolean writeable;
    private final boolean modifiable;
    private final PersistentType persistentType;

    private final Class<? extends TypeHandler<?>> typeHandler;

    private final List<Class<?>> views;

    private final boolean insertable;
    private final boolean updatable;
    private final boolean selectable;

    public QPropertyImpl(BuilderImpl<T, E> builder) {
//        super(null);
        this.entity = builder.entity;
        this.property = builder.property;
        this.path = builder.path;
        this.name = builder.name;
        this.column = builder.column;
        this.idProperty = builder.idProperty;
        this.writeable = builder.isWriteable;
        this.modifiable = builder.isModifiable;
        this.persistentType = builder.persistentType;

        this.typeHandler = builder.typeHandler;

        this.views = Assert.isEmpty(builder.views) ? new ArrayList<>() : builder.views;

        this.insertable = builder.insertable;
        this.updatable = builder.updatable;
        this.selectable = builder.selectable;
    }

    @Override
    public E getEntity() {
        return this.entity;
    }

    @Override
    public Property getProperty() {
        return this.property;
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

    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public boolean isIdProperty() {
        return idProperty;
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
    public PersistentType getPersistentType() {
        return this.persistentType;
    }

    @Override
    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return this.typeHandler;
    }

    @Override
    public boolean isArray() {
        return property.isArray();
    }

    @Override
    public boolean hasView(Class<?> view) {
        if (view == null) return true;
        for (Class<?> item : views) {
            if (item.isAssignableFrom(view)) return true;
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

    @Override
    public String toString() {
        return "path=" + path +
                ",name=" + name +
                ",column=" + column +
                ",javaType=" + getType().getCanonicalName();
    }


    public static class BuilderImpl<T, E extends QEntity> implements Builder<T> {
        private final E entity;
        private final Property property;
        private String path;
        private String name;
        private String column;

        private boolean idProperty = false;
        private boolean isWriteable = true;
        private boolean isModifiable = true;

        private PersistentType persistentType;

        private Class<? extends TypeHandler<?>> typeHandler;
        private List<Class<?>> views;
        private boolean insertable = true;
        private boolean updatable = true;
        private boolean selectable = true;

        public BuilderImpl(E entity, Property property) {
            this.entity = entity;
            this.property = property;
        }

        @Override
        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        @Override
        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder<T> column(String column) {
            this.column = column;
            return this;
        }

        @Override
        public Builder<T> idProperty(boolean idProperty) {
            this.idProperty = idProperty;
            return this;
        }

        @Override
        public Builder<T> writeable(boolean writeable) {
            this.isWriteable = writeable;
            return this;
        }

        @Override
        public Builder<T> modifiable(boolean modifiable) {
            this.isModifiable = isModifiable;
            return this;
        }

        @Override
        public Builder<T> persistentType(PersistentType persistentType) {
            this.persistentType = persistentType;
            return this;
        }

        @Override
        public Builder<T> typeHandler(Class<? extends TypeHandler<?>> typeHandler) {
            this.typeHandler = typeHandler;
            return this;
        }

        @Override
        public Builder<T> views(List<Class<?>> views) {
            this.views = views;
            return this;
        }

        @Override
        public Builder<T> insertable(boolean insertable) {
            this.insertable = insertable;
            return this;
        }

        @Override
        public Builder<T> updatable(boolean updatable) {
            this.updatable = updatable;
            return this;
        }

        @Override
        public Builder<T> selectable(boolean selectable) {
            this.selectable = selectable;
            return this;
        }

        @Override
        public QProperty<T> build() {
            return new QPropertyImpl<>(this);
        }
    }
}
