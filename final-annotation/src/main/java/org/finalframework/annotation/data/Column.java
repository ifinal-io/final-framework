package org.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * Annotate a {@linkplain java.lang.reflect.Field property} or {@linkplain java.lang.reflect.Method property} mapping to a column of datasource.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see NameConverter
 * @see Table
 * @see Reference
 * @see Json
 * @see Virtual
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
     * <pre>
     *     <code>
     *          #{value,javaType=,typeHandler}
     *     </code>
     * </pre>
     *
     * @return the column writer
     */
    String writer() default "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    /**
     * <pre>
     *     <code>
     *         column
     *     </code>
     * </pre>
     *
     * @return the column reader
     */
    String reader() default "${column}";

    Class<? extends ColumnHandler> handler() default ColumnHandler.class;
}
