package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the element is a {@link WriteOnly} property which will not be generated {@literal select}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Column
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteOnly {

}
