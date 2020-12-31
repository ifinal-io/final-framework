package org.ifinal.finalframework.data.mapping;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.data.NameConverter;
import org.ifinal.finalframework.annotation.data.NonCompare;
import org.ifinal.finalframework.annotation.data.Table;
import org.ifinal.finalframework.data.mapping.converter.NameConverterRegistry;
import org.ifinal.finalframework.data.serializer.EntityJsonSerializer;
import org.ifinal.finalframework.util.Asserts;
import org.ifinal.finalframework.util.stream.Streamable;
import org.springframework.data.mapping.PersistentEntity;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonSerialize(using = EntityJsonSerializer.class)
public interface Entity<T> extends PersistentEntity<T, Property>, Streamable<Property>, Iterable<Property> {

    /**
     * return the entity from the {@link Class clazz}
     *
     * @param entityClass entity class
     * @param <T>         entity type
     * @return entity
     */
    static <T> Entity<T> from(final Class<T> entityClass) {

        Asserts.isNull(entityClass, "entityClass must not be null!");
        return EntityCache.getInstance().get(entityClass);
    }

    @SuppressWarnings("unchecked")
    static <T> List<CompareProperty> compare(final T before, final T after) {

        Entity<T> entity = (Entity<T>) from(before.getClass());
        return entity.stream()
            .filter(it -> !it.isTransient() && !it.isAnnotationPresent(NonCompare.class))
            .map(property -> CompareProperty.builder()
                .property(property)
                .value(property.get(before), property.get(after))
                .build())
            .collect(Collectors.toList());
    }

    default String getSimpleName() {
        return getType().getSimpleName();
    }

    default String getTable() {
        NameConverter nameConverter = NameConverterRegistry.getInstance().getTableNameConverter();
        if (isAnnotationPresent(Table.class)) {
            Table table = getRequiredAnnotation(Table.class);
            if (Asserts.nonEmpty(table.value())) {
                return nameConverter.convert(table.value());
            }
        }

        return nameConverter.convert(getSimpleName());
    }

    /**
     * return an instance of this entity
     *
     * @return an instance
     */
    default T getInstance() {
        try {
            return getType().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                String.format("the entity of %s must have no args constructor!", getType().getName()));
        }
    }

}
