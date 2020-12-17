package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.ifinal.finalframework.annotation.core.lang.Transient;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * Annotate a property of {@linkplain Field field} or {@linkplain Method method} mapping to a column of data table.
 *
 * @author likly
 * @version 1.0.0
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
 * @since 1.0.0
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
    String writer() default "#{${value}#if($typeHandler)"
        + "#if($javaType), javaType=$!{javaType.canonicalName}#end"
        + ", typeHandler=$!{typeHandler.canonicalName}#end}";

    /**
     * <pre class="code">
     *      column
     * </pre>
     *
     * @return the column reader
     */
    String reader() default "${column}";

    /**
     * return the property java type
     *
     * @return the property java type
     */
    Class<?> javaType() default Object.class;

}
