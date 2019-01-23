package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.annotation.MultiColumn;
import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.mapping.Property;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 20:05:50
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class BaseQEntity<ID extends Serializable, T > implements QEntity<ID, T> {
    private final Entity<T> entity;
    private final List<QProperty> properties = new ArrayList<>();
    private final Map<String, QProperty> propertiesCache = new HashMap<>();
    private QProperty idProperty;

    public BaseQEntity(Entity<T> entity) {
        this.entity = entity;
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.hasAnnotation(MultiColumn.class)) {
                        final Class multiType = property.getType();
                        final Entity<Property> multiEntity = Entity.from(multiType);
                        Arrays.stream(property.findAnnotation(MultiColumn.class).properties())
                                .map(multiEntity::getRequiredPersistentProperty)
                                .forEach(multiProperty -> {
                                    final String path = property.getName() + "." + multiProperty.getName();
                                    final String name = multiProperty.isIdProperty() ? property.getName()
                                            : property.getName() + multiProperty.getName().substring(0, 1).toUpperCase() + multiProperty.getName().substring(1);
                                    final String column = multiProperty.isIdProperty() ? property.getColumn()
                                            : property.getColumn() + multiProperty.getColumn().substring(0, 1).toUpperCase() + multiProperty.getColumn().substring(1);
                                    final QProperty multiQProperty = new BaseQProperty(multiProperty, property.getTable(), path, name, column);
                                    addProperty(multiQProperty);
                                });

                    } else {
                        QProperty qProperty = new BaseQProperty(property, property.getTable(), property.getName(), property.getName(), property.getColumn());
                        addProperty(qProperty);
                    }
                });
    }

    @Override
    public Class<ID> getIdType() {
        return (Class<ID>) entity.getRequiredIdProperty().getType();
    }

    @Override
    public Class<T> getType() {
        return entity.getType();
    }

    @Override
    public <E> QProperty<E> getIdProperty() {
        return (QProperty<E>) idProperty;
    }

    @Override
    public <E> QProperty<E> getProperty(String name) {
        return (QProperty<E>) propertiesCache.get(name);
    }


    @Override
    public Stream<QProperty> stream() {
        return properties.stream();
    }

    @Override
    public Iterator<QProperty> iterator() {
        return properties.iterator();
    }

    public void addProperty(QProperty property) {
        if (property.isIdProperty()) {
            idProperty = property;
            properties.add(0, property);
        } else {
            properties.add(property);
        }
        propertiesCache.put(property.getPath(), property);
    }


}
