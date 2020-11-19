package org.finalframework.annotation.query;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @since 1.0
 */
@Criterion
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndWith {


    @AliasFor(annotation = Criterion.class, attribute = "property")
    String property() default "";

    @AliasFor(annotation = Criterion.class, attribute = "value")
    String[] value() default {
            "<script>",
            "   <if test=\"${value} != null and ${value} != ''\">",
            "        ${andOr} ${column} LIKE CONCAT(#{${value}},'%') ",
            "   </if>",
            "</script>"
    };

    @AliasFor(annotation = Criterion.class, attribute = "javaType")
    Class<?> javaType() default Object.class;
}
