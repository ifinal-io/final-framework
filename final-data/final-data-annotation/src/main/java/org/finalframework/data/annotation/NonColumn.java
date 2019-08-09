package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * Mark the property is not a column.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Transient
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Transient
@Transient
@Deprecated
public @interface NonColumn {

}
