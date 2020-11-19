package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @see NotIn
 * @since 1.0
 */
@Criterion(In.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface In {
    String property() default "";

    String[] value() default {
            "<if test=\"${value} != null\">",
            "    <foreach collection=\"${value}\" item=\"item\" open=\"${andOr} ${column} IN (\" close=\")\" separator=\",\">#{item}</foreach>",
            "</if>"
    };

    Class<?> javaType() default Object.class;
}
