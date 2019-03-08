package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.annotation.enums.ReferenceMode;
import com.ilikly.finalframework.data.mapping.Entity;

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
public class BaseQEntity<ID extends Serializable, T> implements QEntity<ID, T> {
    private final Entity<T> entity;
    private final List<QProperty> properties = new ArrayList<>();
    private final Map<String, QProperty> propertiesCache = new HashMap<>();
    private QProperty idProperty;

    public BaseQEntity(Entity<T> entity) {
        this.entity = entity;
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class multiType = property.getType();
                        final Entity<?> referenceEntity = Entity.from(multiType);
                        property.referenceProperties().stream()
                                .map(referenceEntity::getRequiredPersistentProperty)
                                .forEach(referenceProperty -> {
                                    final String path = property.getName() + "." + referenceProperty.getName();
                                    final String name = referenceProperty.isIdProperty() ? property.getName()
                                            : property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty.getName().substring(1);

                                    final String referenceColumn = property.referenceColumn(property.getName()) != null ?
                                            property.referenceColumn(property.getName()) : referenceProperty.getColumn();

                                    final String column = referenceProperty.isIdProperty() && property.referenceMode() == ReferenceMode.SIMPLE ?
                                            property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);

                                    final QProperty multiQProperty = new BaseQProperty(referenceProperty, property.getTable(), path, name, column);
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
