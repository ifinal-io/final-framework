package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @see IsNotNull
 * @since 1.0
 */
@Criterion(IsNull.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNull {
    String property() default "";

    String[] value() default {
            "<if test=\"${value} != null\">",
            "<![CDATA[ ${andOr} ${column} IS NULL]]>",
            "</if>"
    };

    Class<?> javaType() default Object.class;
}
