package org.ifinal.finalframework.data.annotation;

import org.ifinal.finalframework.core.annotation.IEntity;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * Annotate the {@linkplain Field property} is a {@literal reference} column which mapping multi columns in datasource,
 * and the {@linkplain Class type} of this {@linkplain Field property} must {@literal implements} the {@link IEntity entity} interface.
 *
 * @author likly
 * @version 1.0.0
 * @see IEntity
 * @since 1.0.0
 */
@Column
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Reference
public @interface Reference {

    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";

    ReferenceMode mode() default ReferenceMode.SIMPLE;

    String[] properties();

    String delimiter() default ":";
}
