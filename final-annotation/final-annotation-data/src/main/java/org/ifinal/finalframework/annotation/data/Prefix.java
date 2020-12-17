package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.lang.NonNull;

/**
 * Annotation the property which have a {@linkplain Prefix#value() prefix} value.
 *
 * @author likly
 * @version 1.0.0
 * @see Virtual
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Prefix {

    @NonNull
    String value();

}
