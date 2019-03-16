package org.finalframework.data.annotation;

import org.springframework.data.annotation.Transient;

import java.lang.annotation.*;

/**
 * Mark the property is not a column.
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 * @see Transient
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transient
public @interface NonColumn {

}
