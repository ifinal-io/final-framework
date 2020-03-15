package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * The property annotated by {@link WriteOnlyColumn} will not be selected.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteOnlyColumn {

}
