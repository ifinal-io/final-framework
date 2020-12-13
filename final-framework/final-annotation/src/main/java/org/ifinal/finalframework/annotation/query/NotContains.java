package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @see Like
 * @since 1.0.0
 */
@Criterion(NotContains.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotContains {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null and ${value} != ''\">",
        "    ${andOr} ${column} NOT LIKE CONCAT('%',#{${value}},'%') ",
        "</if>",
    };

    Class<?> javaType() default Object.class;

}
