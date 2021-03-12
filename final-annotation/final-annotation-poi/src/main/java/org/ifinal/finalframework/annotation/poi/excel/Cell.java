package org.ifinal.finalframework.annotation.poi.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cell.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {

    int index() default -1;

    String value();

    String header() default "";

}
