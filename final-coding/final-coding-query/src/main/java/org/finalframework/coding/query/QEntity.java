package org.finalframework.coding.query;

import org.finalframework.coding.annotation.Template;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.file.JavaSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:24
 * @since 1.0
 */
@Template("query/entity.jvm")
public class QEntity implements JavaSource {
    private final String packageName;
    private final String name;
    private final String simpleName;
    private final Entity<Property> entity;
    private final List<QProperty> properties;
    private final QProperty idProperty;

    private QEntity(Builder builder) {
        this.entity = builder.entity;
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
        this.properties = builder.properties;
        this.idProperty = builder.idProperty;
    }

    public static Builder builder(Entity<Property> entity) {
        return new Builder(entity);
    }

    public Entity<Property> getEntity() {
        return entity;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    public List<QProperty> getProperties() {
        return properties;
    }

    public QProperty getIdProperty() {
        return idProperty;
    }

    public static class Builder implements org.finalframework.core.Builder<QEntity> {
        private final Entity<Property> entity;
        private String packageName;
        private String simpleName;
        private List<QProperty> properties = new ArrayList<>();
        private QProperty idProperty;

        public Builder(Entity<Property> entity) {
            this.entity = entity;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder name(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder addProperty(QProperty property) {
            if (property.isIdProperty()) {
                idProperty = property;
                properties.add(0, property);
            } else {
                this.properties.add(property);
            }
            return this;
        }

        @Override
        public QEntity build() {
            return new QEntity(this);
        }
    }


}