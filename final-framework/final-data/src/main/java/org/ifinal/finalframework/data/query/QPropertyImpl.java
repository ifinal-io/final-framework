package org.ifinal.finalframework.data.query;

import org.springframework.lang.Nullable;

import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.query.QEntity;
import org.ifinal.finalframework.query.QProperty;
import org.ifinal.finalframework.util.Asserts;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.TypeHandler;

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

    public QPropertyImpl(final Builder<T, E> builder) {

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

    public Property getProperty() {
        return this.property;
    }

    @Override
    public Class<T> getType() {
        return null;
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
    public String getWriter() {
        return null;
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
        return false;
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
        return false;
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

        private boolean idProperty = false;

        private boolean isReadable = true;

        private boolean isWriteable = true;

        private boolean isModifiable = true;

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
