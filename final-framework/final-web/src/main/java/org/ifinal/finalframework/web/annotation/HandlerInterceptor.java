package org.ifinal.finalframework.web.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * {@literal Spring} 拦截器注解 {@link java.lang.annotation.Annotation}声明方式。
 *
 * @author likly
 * @version 1.0.0
 * @see InterceptorRegistration
 * @see org.springframework.web.servlet.HandlerInterceptor
 * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
 * @since 1.0.0
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerInterceptor {
    /**
     * @return 拦截器包含的路径表达式
     * @see InterceptorRegistration#addPathPatterns(String...)
     * @see InterceptorRegistration#addPathPatterns(List)
     */
    String[] includes() default {};

    /**
     * @return 拦截器排除的路径表达式
     * @see InterceptorRegistration#excludePathPatterns(String...)
     * @see InterceptorRegistration#excludePathPatterns(List)
     */
    String[] excludes() default {};
}
