package org.ifinal.finalframework.annotation.data;

import org.apache.ibatis.type.TypeHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Column(
        writer = "ST_GeomFromText(#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end})",
        reader = "ST_ASTEXT(${column}) as ${column}"
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Geometry {

    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";

    @AliasFor(annotation = Column.class)
    Class<? extends TypeHandler> typeHandler() default TypeHandler.class;

}