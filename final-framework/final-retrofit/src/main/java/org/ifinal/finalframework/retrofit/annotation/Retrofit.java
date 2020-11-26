package org.ifinal.finalframework.retrofit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a href="https://square.github.io/retrofit/">A type-safe HTTP client for Android and Java.</a>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Retrofit {
    String value() default "";
}
