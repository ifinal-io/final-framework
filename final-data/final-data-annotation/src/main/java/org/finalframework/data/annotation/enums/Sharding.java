package org.finalframework.data.annotation.enums;

import org.finalframework.data.annotation.Column;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-19 09:11:44
 * @since 1.0
 */
@Column
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sharding {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
