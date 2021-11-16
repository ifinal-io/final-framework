/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.web.annotation.servlet;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * A custom {@link Component} annotation for {@link HandlerInterceptor} and the interceptor would be auto-detects by a
 * custom a {@link WebMvcConfigurer}.
 *
 * <pre class="code">
 * &#064;Interceptor(includes="/**")
 * public class MyHandlerInterceptor implements HandlerInterceptor{
 *     ...
 * }
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @see InterceptorRegistration
 * @see HandlerInterceptor
 * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
 * @see org.ifinalframework.web.mvc.config.HandlerInterceptorWebMvcConfigurer
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
