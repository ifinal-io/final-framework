package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Order.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    String property() default "";

    int order() default 0;

    String[] value() default {
        "<if test=\"${value} != null\">",
        "   ${column} \\${${value}},",
        "</if>"
    };

}
