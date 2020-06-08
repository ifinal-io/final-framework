package org.finalframework.data.query;

import org.finalframework.data.annotation.Table;
import org.finalframework.data.annotation.View;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.MappingUtils;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-21 10:34:30
 * @since 1.0
 */
public class AbsQEntity<ID extends Serializable, T> implements QEntity<ID, T> {

    private final List<QProperty<?>> properties = new ArrayList<>();
    private final Map<String, QProperty<?>> pathProperties = new HashMap<>();

    private final Class<T> type;
    private final String table;

    private QProperty<?> idProperty;

    public AbsQEntity(Class<T> type) {
        this(type, NameConverterRegistry.getInstance().getTableNameConverter().convert(
                type.getAnnotation(Table.class) == null || type.getAnnotation(Table.class).value().isEmpty()
                        ? type.getSimpleName()
                        : type.getAnnotation(Table.class).value()
        ));
    }

    public AbsQEntity(Class<T> type, String table) {
        this.type = type;
        this.table = table;
        this.initProperties();
    }

    protected void initProperties() {
        Entity.from(type)
                .stream()
                .filter(it -> !it.isTransient())
                .forEach(property -> {

                    final View view = property.findAnnotation(View.class);
                    final List<Class<?>> views = Optional.ofNullable(view).map(value -> Arrays.asList(value.value())).orElse(null);
                    if (property.isReference()) {

                        final Entity<?> referenceEntity = Entity.from(property.getType());

                        property.getReferenceProperties()
                                .stream()
                                .map(referenceEntity::getRequiredPersistentProperty)
                                .forEach(referenceProperty -> {
                                    addProperty(
                                            QProperty.builder(this, referenceProperty)
                                                    .path(property.getName() + "." + referenceProperty.getName())
                                                    .name(MappingUtils.formatPropertyName(property, referenceProperty))
                                                    .column(MappingUtils.formatColumn(property, referenceProperty))
                                                    .views(views)
                                                    .writeable(property.isWriteable())
                                                    .build()
                                    );
                                });


                    } else {
                        addProperty(
                                QProperty.builder(this, property)
                                        .path(property.getName())
                                        .name(property.getName())
                                        .column(MappingUtils.formatColumn(null, property))
                                        .idProperty(property.isIdProperty())
                                        .writeable(property.isWriteable())
                                        .views(views)
                                        .build()
                        );
                    }
                });
    }

    public void addProperty(QProperty<?> property) {
        this.properties.add(property);
        this.pathProperties.put(property.getPath(), property);
        if (property.isIdProperty()) {
            this.idProperty = property;
        }
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

    @Override
    public QProperty<ID> getIdProperty() {
        return (QProperty<ID>) this.idProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E> getProperty(String path) {
        return (QProperty<E>) pathProperties.get(path);
    }

    @Override
    public Iterator<QProperty<?>> iterator() {
        return properties.iterator();
    }

    @Override
    public Stream<QProperty<?>> stream() {
        return properties.stream();
    }
}
