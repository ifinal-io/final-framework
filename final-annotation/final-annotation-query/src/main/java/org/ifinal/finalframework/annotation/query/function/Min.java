package org.ifinal.finalframework.annotation.query.function;


/**
 * Max.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Function(value = Min.class)
public @interface Min {

    String[] value() default "MIN($(column))";

}
