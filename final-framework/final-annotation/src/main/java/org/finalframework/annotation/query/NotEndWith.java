package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @see Like
 * @since 1.0
 */
@Criterion(EndWith.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEndWith {
    String property() default "";

    String[] value() default {
            "<if test=\"${value} != null and ${value} != ''\">",
            "    ${andOr} ${column} NOT LIKE CONCAT(#{${value}},'%') ",
            "</if>"
    };

    Class<?> javaType() default Object.class;
}
