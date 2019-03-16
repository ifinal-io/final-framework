package org.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.LastModifiedDate;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Index(Integer.MAX_VALUE - 99)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LastModifiedDate
public @interface LastModifiedTime {
    String table() default "";

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default false;

    boolean updatable() default true;

    boolean selectable() default true;

    boolean placeholder() default true;
}
