package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate the element of {@link Field},{@link Method},{@link Class} is {@link Transient}.
 *
 * @author likly
 * @version 1.0.0
 * @see Column
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Transient
public @interface Transient {

}
