package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @see NotJsonContains
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Criterion(JsonContains.class)
public @interface JsonContains {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null\">",
        "   <![CDATA[",
        "       ${andOr} JSON_CONTAINS( ${column}, #{${value}",
        "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
        "           #if($typeHandler), typeHandler=$!{typeHandler.canonicalName}#end}",
        "       #if($path), '${path}'#end)",
        "   ]]>",
        "</if>"
    };

    String path() default "";

    Class<?> javaType() default Object.class;

}
