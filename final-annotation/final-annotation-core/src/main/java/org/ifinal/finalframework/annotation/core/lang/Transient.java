package org.ifinal.finalframework.annotation.core.lang;

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
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transient {

}
