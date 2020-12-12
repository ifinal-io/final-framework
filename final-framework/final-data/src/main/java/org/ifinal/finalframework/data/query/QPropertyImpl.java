package org.ifinal.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.util.Asserts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
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

    private final boolean idProperty;
    private final boolean readable;
    private final boolean writeable;
    private final boolean modifiable;

    private final Class<? extends TypeHandler> typeHandler;

    private final List<Class<?>> views;


    public QPropertyImpl(final BuilderImpl<T, E> builder) {

        this.entity = builder.entity;
        this.property = builder.property;
        this.order = builder.order;
        this.path = builder.path;
        this.name = builder.name;
        this.column = builder.column;

        this.idProperty = builder.idProperty;
        this.readable = builder.isReadable;
        this.writeable = builder.isWriteable;
        this.modifiable = builder.isModifiable;

        this.typeHandler = builder.typeHandler;

        this.views = Asserts.isEmpty(builder.views) ? new ArrayList<>() : builder.views;

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


    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public boolean isIdProperty() {
        return idProperty;
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
    public boolean isArray() {
        return property.isArray();
    }

    @Override
    public boolean hasView(final Class<?> view) {

        if (view == null || Asserts.isEmpty(views)) return true;
        for (Class<?> item : views) {
            if (item.isAssignableFrom(view) || item.equals(view)) return true;
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
        private Integer order = 0;
        private String path;
        private String name;
        private String column;

        private boolean idProperty = false;
        private boolean isReadable = true;
        private boolean isWriteable = true;
        private boolean isModifiable = true;


        private Class<? extends TypeHandler> typeHandler;
        private List<Class<?>> views;

        public BuilderImpl(final E entity, final Property property) {

            this.entity = entity;
            this.property = property;
        }

        @Override
        public Builder<T> order(final Integer order) {

            this.order = order;
            return this;
        }

        @Override
        public Builder<T> path(final String path) {

            this.path = path;
            return this;
        }

        @Override
        public Builder<T> name(final String name) {

            this.name = name;
            return this;
        }

        @Override
        public Builder<T> column(final String column) {

            this.column = column;
            return this;
        }

        @Override
        public Builder<T> idProperty(final boolean idProperty) {

            this.idProperty = idProperty;
            return this;
        }

        @Override
        public Builder<T> readable(final boolean readable) {

            this.isReadable = readable;
            return this;
        }

        @Override
        public Builder<T> writeable(final boolean writeable) {

            this.isWriteable = writeable;
            return this;
        }

        @Override
        public Builder<T> modifiable(final boolean modifiable) {

            this.isModifiable = modifiable;
            return this;
        }


        @Override
        public Builder<T> typeHandler(final Class<? extends TypeHandler> typeHandler) {

            this.typeHandler = typeHandler;
            return this;
        }

        @Override
        public Builder<T> views(final List<Class<?>> views) {

            this.views = views;
            return this;
        }


        @Override
        public QProperty<T> build() {
            return new QPropertyImpl<>(this);
        }
    }
}
