package org.finalframework.annotation.query;

import org.apache.ibatis.type.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     column = #{value}
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @see NotEqual
 * @since 1.0
 */
@Criterion(Equal.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Equal {
    String property() default "";

    String[] value() default {
            "   <if test=\"${value} != null\">",
            "   <![CDATA[ ${andOr} ${column} = #{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}]]>",
            "   </if>"
    };

    Class<?> javaType() default Object.class;

    Class<? extends TypeHandler> typeHandler() default TypeHandler.class;

}
