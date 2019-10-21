package org.finalframework.data.coding.entity;

import org.finalframework.data.annotation.enums.PersistentType;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 21:43:22
 * @since 1.0
 */
public class QProperty implements Serializable {

    private static final long serialVersionUID = 737464778206273859L;
    private final String path;
    private final String name;
    private final String column;
    private final String type;
    private final boolean idProperty;
    private final PersistentType persistentType;
    private final boolean insertable;
    private final boolean updatable;
    private final boolean selectable;

    private QProperty(Builder builder) {
        this.path = builder.path;
        this.name = builder.name;
        this.column = builder.column;
        this.type = builder.type;
        this.idProperty = builder.idProperty;
        this.persistentType = builder.persistentType;
        this.insertable = builder.insertable;
        this.updatable = builder.updatable;
        this.selectable = builder.selectable;
    }

    public static Builder builder(String path, String name) {
        return new Builder(path, name);
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getColumn() {
        return column;
    }

    public boolean isIdProperty() {
        return idProperty;
    }

    public String getType() {
        return type;
    }

    public PersistentType getPersistentType() {
        return persistentType;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public static class Builder implements org.finalframework.core.Builder<QProperty> {

        private final String path;
        private final String name;
        private String column;
        private String type;
        private PersistentType persistentType;
        private boolean idProperty;

        private boolean insertable;
        private boolean updatable;
        private boolean selectable;

        private Builder(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public Builder column(String column) {
            this.column = column;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder persistentType(PersistentType persistentType) {
            this.persistentType = persistentType;
            return this;
        }

        public Builder idProperty(boolean idProperty) {
            this.idProperty = idProperty;
            return this;
        }

        public Builder updatable(boolean updatable) {
            this.updatable = updatable;
            return this;
        }

        public Builder insertable(boolean insertable) {
            this.insertable = insertable;
            return this;
        }

        public Builder selectable(boolean selectable) {
            this.selectable = selectable;
            return this;
        }


        @Override
        public QProperty build() {
            return new QProperty(this);
        }
    }

}
