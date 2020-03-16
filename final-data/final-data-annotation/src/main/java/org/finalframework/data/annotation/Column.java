package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * 标记一个 {@link java.lang.reflect.Field} 或者 {@link java.lang.reflect.Method} 为数据库和列。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
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

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
