package org.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * Mark the target is a function column.
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-23 13:07:44
 * @since 1.0
 */
@Column
@Persistent
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionColumn {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String writer() default "";

    String reader() default "";
}
