package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.mapping.Property;

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
    private final PersistentType persistentType;

    private final Class<? extends TypeHandler> typeHandler;

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
        this.persistentType = builder.persistentType;

        this.typeHandler = builder.typeHandler;

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
    public PersistentType getPersistentType() {
        return this.persistentType;
    }

    @Override
    public Class<? extends TypeHandler> getTypeHandler() {
        return this.typeHandler;
    }

    @Override
    public boolean isArray() {
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
        private PersistentType persistentType;

        private Class<? extends TypeHandler> typeHandler;

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
        public Builder<T> persistentType(PersistentType persistentType) {
            this.persistentType = persistentType;
            return this;
        }

        @Override
        public Builder<T> typeHandler(Class<? extends TypeHandler> typeHandler) {
            this.typeHandler = typeHandler;
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
