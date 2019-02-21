package com.ilikly.finalframework.data.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * Mark the target is a function column.
 * @author likly
 * @version 1.0
 * @date 2019-01-23 13:07:44
 * @since 1.0
 */
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Persistent
@Documented
public @interface FunctionColumn {
    String writer();

    String reader();
}
