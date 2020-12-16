package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre class="code">
 *      column LIKE CONCAT('%',#{value})
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @see Like
 * @see NotStartWith
 * @since 1.0.0
 */
@Criterion(StartWith.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartWith {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null and ${value} != ''\">",
        "     ${andOr} ${column} LIKE CONCAT('%',#{${value}}) ",
        "</if>"
    };

    Class<?> javaType() default Object.class;

}
