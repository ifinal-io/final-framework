package org.finalframework.spring.web.interceptor.annotation;

import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:15
 * @since 1.0
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringFactory(value = org.springframework.web.servlet.HandlerInterceptor.class,expand = true)
public @interface HandlerInterceptor {
    String[] includes() default {};

    String[] excludes() default {};
}
