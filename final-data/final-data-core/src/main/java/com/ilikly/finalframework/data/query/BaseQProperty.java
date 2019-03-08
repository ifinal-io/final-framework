package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.mapping.Property;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegistry;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 20:02:52
 * @since 1.0
 */
public class BaseQProperty<T> implements QProperty<T> {

    private final Property property;
    private final String table;
    private final String path;
    private final String name;
    private final String column;

    public BaseQProperty(Property property, String table, String path, String name, String column) {
        this.property = property;
        this.table = table;
        this.path = path;
        this.name = name;
        this.column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
    }

    @Override
    public Class<T> getType() {
        return (Class<T>) property.getType();
    }

    @Override
    public Class getComponentType() {
        return property.getComponentType();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public @NotNull String getTable() {
        return table;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getColumn() {
        return column;
    }

    @Override
    public @NotNull PersistentType getPersistentType() {
        return property.getPersistentType();
    }

    @Override
    public boolean isEntity() {
        return property.isEntity();
    }

    @Override
    public boolean isIdProperty() {
        return !path.contains(".") && property.isIdProperty();
    }

    @Override
    public boolean isEnum() {
        return property.isEnum();
    }

    @Override
    public boolean isCollectionLike() {
        return property.isCollectionLike();
    }

    @Override
    public boolean isMap() {
        return property.isMap();
    }

    @Override
    public boolean isTransient() {
        return property.isTransient();
    }

    @Override
    public boolean isArray() {
        return property.isArray();
    }

    @Override
    public boolean unique() {
        return property.unique();
    }

    @Override
    public boolean nonnull() {
        return property.nonnull();
    }

    @Override
    public boolean insertable() {
        return property.insertable();
    }

    @Override
    public boolean updatable() {
        return property.updatable();
    }

    @Override
    public <A extends Annotation> A findAnnotation(Class<A> ann) {
        return property.findAnnotation(ann);
    }


}
