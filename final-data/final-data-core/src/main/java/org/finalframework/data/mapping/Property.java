package org.finalframework.data.mapping;

import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface Property extends PersistentProperty<Property> {

    @NotNull
    String getTable();

    @NotNull
    String getColumn();

    @NotNull
    PersistentType getPersistentType();

    default boolean isEnum() {
        return getType().isEnum();
    }

    boolean hasView(@NonNull Class<?> view);

    boolean unique();

    boolean nonnull();

    boolean insertable();

    boolean updatable();

    boolean selectable();

    boolean placeholder();

    boolean isReference();

    ReferenceMode referenceMode();

    List<String> referenceProperties();

    String referenceColumn(@NonNull String property);

    default Object get(@NotNull Object target) {
        try {
            return getRequiredGetter().invoke(target);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    default void set(@NotNull Object target, Object value) {
        try {
            getRequiredSetter().invoke(target, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    default <A extends Annotation> boolean hasAnnotation(Class<A> ann) {
        return findAnnotation(ann) != null;
    }
}
