package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation the property which have a {@linkplain Prefix#value() prefix} value.
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-11 15:36:18
 * @see Virtual
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Prefix {
    String value();
}
