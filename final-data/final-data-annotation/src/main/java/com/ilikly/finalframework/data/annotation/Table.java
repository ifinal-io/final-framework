package com.ilikly.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.lang.NonNull;

import java.lang.annotation.*;

/**
 * mark the entity table name if the entity name if not match the database table name.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    @AliasFor("name")
    @NonNull
    String value() default "";

    @AliasFor("value")
    @NonNull
    String name() default "";
}
