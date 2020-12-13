package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Criterion(EndWith.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndWith {

    String property() default "";

    String[] value() default {
        "<script>",
        "   <if test=\"${value} != null and ${value} != ''\">",
        "        ${andOr} ${column} LIKE CONCAT(#{${value}},'%') ",
        "   </if>",
        "</script>"
    };

    Class<?> javaType() default Object.class;

}
