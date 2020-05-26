package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * Marked the {@link Field} is a {@literal reference} column, and the {@linkplain Class type} of {@link Field} must
 * {@literal implements} the {@link IEntity interface}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see IEntity
 * @since 1.0
 */
@Column
@Persistent
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
