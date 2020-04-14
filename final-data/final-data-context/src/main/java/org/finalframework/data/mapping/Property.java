package org.finalframework.data.mapping;

import org.finalframework.data.annotation.*;
import org.springframework.data.mapping.PersistentProperty;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface Property extends PersistentProperty<Property> {

    default boolean isEnum() {
        return getType().isEnum();
    }

    String getColumn();

    /**
     * @return true if don't have {@link Transient} annotation and have {@link Default} annotaion.
     * @see Default
     */
    boolean isDefault();

    /**
     * @see Final
     */
    boolean isFinal();

    /**
     * @see Virtual
     */
    boolean isVirtual();

    /**
     * @see Sharding
     */
    boolean isSharding();

    /**
     * @see ReadOnly
     */
    boolean isReadOnly();


    /**
     * @see WriteOnly
     */
    boolean isWriteOnly();

    default boolean isWriteable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isDefault();
    }

    default boolean isModifiable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isFinal();
    }

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
