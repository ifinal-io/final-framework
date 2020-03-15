package org.finalframework.data.annotation;

import org.springframework.data.annotation.ReadOnlyProperty;

import java.lang.annotation.*;

/**
 * The property annotated by {@link ReadOnlyColumn} will not be updated.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@ReadOnlyProperty
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnlyColumn {

}
