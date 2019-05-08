package org.finalframework.data.annotation;

import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.annotation.Reference;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see ReferenceColumn
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
@Reference
@Deprecated
public @interface MultiColumn {

    @AliasFor(value = "name")
    String value() default "";

    @AliasFor(value = "value")
    String name() default "";

    String[] properties();

    String delimiter() default ":";

    ReferenceMode mode() default ReferenceMode.SIMPLE;

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default true;

    boolean updatable() default true;

    boolean selectable() default true;

}
