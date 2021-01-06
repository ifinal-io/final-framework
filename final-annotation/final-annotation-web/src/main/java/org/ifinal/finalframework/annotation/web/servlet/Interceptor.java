package org.ifinal.finalframework.annotation.web.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A custom {@link Component} annotation for {@link HandlerInterceptor}.
 *
 * @author likly
 * @version 1.0.0
 * @see InterceptorRegistration
 * @see HandlerInterceptor
 * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
 * @since 1.0.0
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptor {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * return the path patterns.
     *
     * @return the path patterns.
     * @see InterceptorRegistration#addPathPatterns(String...)
     * @see InterceptorRegistration#addPathPatterns(List)
     */
    String[] includes() default {};

    /**
     * return the exclude path patterns.
     *
     * @return he exclude path patterns.
     * @see InterceptorRegistration#excludePathPatterns(String...)
     * @see InterceptorRegistration#excludePathPatterns(List)
     */
    String[] excludes() default {};

}
