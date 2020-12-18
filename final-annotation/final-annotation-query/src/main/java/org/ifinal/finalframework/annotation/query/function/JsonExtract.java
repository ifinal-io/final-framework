package org.ifinal.finalframework.annotation.query.function;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JsonExtract.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Function(JsonExtract.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonExtract {

    String[] value() default "JSON_EXTRACT(${column},${path})";

    String path() default "";

}
