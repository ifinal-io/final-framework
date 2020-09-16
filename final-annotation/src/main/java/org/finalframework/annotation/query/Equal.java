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
 * @see NotEqual
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Criterion
public @interface Equal {
    @AliasFor(annotation = Criterion.class, attribute = "property")
    String property() default "";

    @AliasFor(annotation = Criterion.class, attribute = "value")
    String[] value() default {
            "   <if test=\"${value} != null\">",
            Constants.EQUAL,
            "   </if>"
    };

    @AliasFor(annotation = Criterion.class, attribute = "javaType")
    Class<?> javaType() default Object.class;

    @AliasFor(annotation = Criterion.class, attribute = "handler")
    Class<? extends CriterionHandler> handler() default CriterionHandler.class;

    @AliasFor(annotation = Criterion.class, attribute = "attributes")
    Attribute[] attributes() default {};
}
