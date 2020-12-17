package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.springframework.core.annotation.AliasFor;

/**
 * Annotate the {@linkplain Field property} is a {@literal reference} column which mapping multi columns in datasource,
 * and the {@linkplain Class type} of this {@linkplain Field property} must {@literal implements} the {@link IEntity
 * entity} interface.
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
