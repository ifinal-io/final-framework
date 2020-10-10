package org.finalframework.annotation.data;

import org.apache.ibatis.type.TypeHandler;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate a property of {@linkplain Field field} or {@linkplain Method method} mapping to a column of data table.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see NameConverter
 * @see Table
 * @see Reference
 * @see Json
 * @see Virtual
 * @see Version
 * @see Created
 * @see Creator
 * @see LastModified
 * @see LastModifier
 * @see Transient
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
public @interface Column {

    /**
     * return the column name of {@code property} mapped.
     *
     * @return column name
     * @see #name()
     */
    @AliasFor("name")
    String value() default "";

    /**
     * @return column name
     * @see #value()
     */
    @AliasFor("value")
    String name() default "";

    /**
     * return the column writer expression
     *
     * <pre class="code">
     *      #{value,javaType=,typeHandler}
     * </pre>
     *
     * @return the column writer
     */
    String writer() default "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    /**
     * <pre class="code">
     *      column
     * </pre>
     *
     * @return the column reader
     */
    String reader() default "${column}";

    Class<? extends ColumnHandler> handler() default ColumnHandler.class;

    /**
     * return the property java type
     *
     * @return the property java type
     */
    Class<?> javaType() default Object.class;

    /**
     * return the property type handler
     *
     * @return the property type handler
     */
    Class<? extends TypeHandler> typeHandler() default TypeHandler.class;
}
