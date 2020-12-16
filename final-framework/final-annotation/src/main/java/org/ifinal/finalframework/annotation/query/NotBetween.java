package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @see Between
 * @since 1.0.0
 */
@Criterion(NotBetween.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBetween {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null and ${value}.min != null and ${value}.max != null\">",
        "   <![CDATA[",
        "       ${andOr} ${column} BETWEEN #{${value}.min",
        "           #if($javaType),javaType=$!{javaType.canonicalName}#end",
        "           #if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}",
        "       AND #{${value}.max",
        "           #if($javaType),javaType=$!{javaType.canonicalName}#end",
        "           #if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}",
        "   ]]>",
        "</if>"
    };

    Class<?> javaType() default Object.class;

}
