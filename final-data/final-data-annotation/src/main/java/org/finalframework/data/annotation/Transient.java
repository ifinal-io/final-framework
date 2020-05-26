package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.processing.Processor;

/**
 * The marked element of {@link Field}、 {@link Method} or {@link Class} is not passed by {@link Processor}
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-08 19:51:19
 * @see Column
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Transient
public @interface Transient {

}
