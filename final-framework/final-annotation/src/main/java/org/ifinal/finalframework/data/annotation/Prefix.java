package org.ifinal.finalframework.data.annotation;

import org.springframework.lang.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation the property which have a {@linkplain Prefix#value() prefix} value.
 *
 * @author likly
 * @version 1.0.0
 * @see Virtual
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Prefix {
    @NonNull
    String value();
}
