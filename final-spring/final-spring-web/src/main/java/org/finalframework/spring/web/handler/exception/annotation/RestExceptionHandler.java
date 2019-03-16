package org.finalframework.spring.web.handler.exception.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被该注解所标记的元素，将会被注册到 {@link Rest}
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:15
 * @since 1.0
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestExceptionHandler {

}
