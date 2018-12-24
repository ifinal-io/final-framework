package cn.com.likly.finalframework.data.mapping;

import cn.com.likly.finalframework.data.annotation.enums.PersistentType;
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

    @NotNull
    String getTable();

    @NotNull
    String getColumn();

    @NotNull
    PersistentType getPersistentType();

    default boolean isEnum() {
        return getType().isEnum();
    }

    boolean unique();

    boolean nonnull();

    boolean insertable();

    boolean updatable();

    Object get(@NotNull Object target);

    void set(@NotNull Object target, Object value);

    default <A extends Annotation> boolean hasAnnotation(Class<A> ann) {
        return findAnnotation(ann) != null;
    }
}
