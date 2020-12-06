package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.data.Table;
import org.ifinal.finalframework.annotation.data.View;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.MappingUtils;
import org.ifinal.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsQEntity<I extends Serializable, T> implements QEntity<I, T> {

    private final List<QProperty<?>> properties = new ArrayList<>();
    private final Map<String, QProperty<?>> pathProperties = new HashMap<>();

    private final Class<T> type;
    private final String table;

    private QProperty<?> idProperty;
    private QProperty<?> versionProperty;

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
        Entity<T> entity = Entity.from(type);

        entity.stream()
                .filter(it -> !it.isTransient())
                .forEach(property -> {

                    final View view = property.findAnnotation(View.class);
                    final List<Class<?>> views = Optional.ofNullable(view).map(value -> Arrays.asList(value.value()))
                            .orElse(null);
                    final int order = property.getOrder();
                    if (property.isReference()) {

                        final Entity<?> referenceEntity = Entity.from(property.getType());

                        AtomicInteger index = new AtomicInteger();

                        property.getReferenceProperties()
                                .stream()
                                .map(referenceEntity::getRequiredPersistentProperty)
                                .forEach(referenceProperty -> addProperty(
                                        QProperty.builder(this, referenceProperty)
                                                .order(order + index.getAndIncrement())
                                                .path(property.getName() + "." + referenceProperty.getName())
                                                .name(MappingUtils.formatPropertyName(property, referenceProperty))
                                                .column(MappingUtils.formatColumn(entity, property, referenceProperty))
                                                .views(views)
                                                .readable(true)
                                                .writeable(property.isWriteable())
                                                .modifiable(property.isModifiable())
                                                .typeHandler(referenceProperty.getTypeHandler())
                                                .build()
                                ));


                    } else {

                        addProperty(
                                QProperty.builder(this, property)
                                        .order(order)
                                        .path(property.getName())
                                        .name(property.getName())
                                        .column(MappingUtils.formatColumn(entity, property, null))
                                        .idProperty(property.isIdProperty())
                                        .readable(!property.isTransient() && !property.isVirtual() && !property.isWriteOnly())
                                        .writeable(property.isWriteable())
                                        .modifiable(property.isModifiable())
                                        .typeHandler(property.getTypeHandler())
                                        .views(views)
                                        .build()
                        );
                    }
                });
        this.properties.sort(Comparator.comparing(QProperty::getOrder));
    }

    private void addProperty(QProperty<?> property) {
        this.properties.add(property);
        this.pathProperties.put(property.getPath(), property);
        if (property.isIdProperty()) {
            this.idProperty = property;
        } else if (property.isVersionProperty()) {
            this.versionProperty = property;
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
    @SuppressWarnings("unchecked")
    public QProperty<I> getIdProperty() {
        return (QProperty<I>) this.idProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E>  getVersionProperty() {
        return (QProperty<E>) this.versionProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> QProperty<E> getProperty(String path) {
        return (QProperty<E>) pathProperties.get(path);
    }

    @Override
    @NonNull
    public Iterator<QProperty<?>> iterator() {
        return properties.iterator();
    }

    @Override
    public Stream<QProperty<?>> stream() {
        return properties.stream();
    }
}
