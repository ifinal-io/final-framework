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
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
@Reference
@Column
public @interface ReferenceColumn {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    ReferenceMode mode() default ReferenceMode.SIMPLE;

    String[] properties();

    String delimiter() default ":";

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default true;

    boolean updatable() default true;

    boolean selectable() default true;

}
