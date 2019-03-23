package org.finalframework.spring.aop.invoke.annotation;

import org.finalframework.spring.aop.annotation.AopAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 13:11:19
 * @since 1.0
 */
@AopAnnotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
    Class<?> target() default Object.class;

    String method() default "";

    Class<?>[] args() default {};
}
