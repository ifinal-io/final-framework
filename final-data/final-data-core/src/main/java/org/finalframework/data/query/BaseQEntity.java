package org.finalframework.data.query;

import org.finalframework.data.annotation.ColumnView;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.query.criterion.BaseQProperty;

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
    private final Map<String, QProperty> namePropertiesCache = new HashMap<>();
    private final Map<String, QProperty> columnPropertiesCache = new HashMap<>();
    private final Set<Class<?>> views = new HashSet<>();
    private QProperty idProperty;

    public BaseQEntity(Entity<T> entity) {
        this.entity = entity;
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class multiType = property.getType();
                        final Entity<?> referenceEntity = Entity.from(multiType);
                        final ColumnView columnView = property.findAnnotation(ColumnView.class);
                        final Class[] views = columnView == null ? null : columnView.value();
                        property.referenceProperties().stream()
                                .map(referenceEntity::getRequiredPersistentProperty)
                                .forEach(referenceProperty -> {
                                    final String path = property.getName() + "." + referenceProperty.getName();
                                    final String name = referenceProperty.isIdProperty() ? property.getName()
                                            : property.getName() + referenceProperty.getName().substring(0, 1).toUpperCase() + referenceProperty.getName().substring(1);

                                    final String referenceColumn = property.referenceColumn(referenceProperty.getName()) != null ?
                                            property.referenceColumn(referenceProperty.getName()) : referenceProperty.getColumn();

                                    final String column = referenceProperty.isIdProperty() && property.referenceMode() == ReferenceMode.SIMPLE ?
                                            property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);

                                    final QProperty multiQProperty = new BaseQProperty(referenceProperty, property.getTable(), path, name, column, views);
                                    addProperty(multiQProperty);
                                });

                    } else {
                        final ColumnView columnView = property.findAnnotation(ColumnView.class);
                        final Class[] views = columnView == null ? null : columnView.value();
                        QProperty qProperty = new BaseQProperty(property, property.getTable(), property.getName(), property.getName(), property.getColumn(), views);
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
        return propertiesCache.get(name);
    }

    @Override
    public <E> QProperty<E> getPropertyByName(String name) {
        return namePropertiesCache.get(name);
    }

    @Override
    public <E> QProperty<E> getPropertyByColumn(String column) {
        return columnPropertiesCache.get(column);
    }

    @Override
    public Collection<Class<?>> getViews() {
        return views;
    }

    @Override
    public Stream<QProperty> stream() {
        return properties.stream();
    }

    @Override
    public Iterator<QProperty> iterator() {
        return properties.iterator();
    }

    private void addViews(Class<?>[] views) {
        if (views != null && views.length > 0) {
            this.views.addAll(Arrays.asList(views));
        }
    }

    private void addProperty(QProperty property) {
        if (property.isIdProperty()) {
            idProperty = property;
            properties.add(0, property);
        } else {
            properties.add(property);
        }
        propertiesCache.put(property.getPath(), property);
        namePropertiesCache.put(property.getName(), property);
        columnPropertiesCache.put(property.getColumn(), property);
    }


}
