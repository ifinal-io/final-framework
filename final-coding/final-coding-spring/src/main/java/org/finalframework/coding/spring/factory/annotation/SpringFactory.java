package org.finalframework.coding.spring.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:24:44
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.boot.autoconfigure.EnableAutoConfiguration
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringFactory {
    /**
     * 工具接口类
     */
    Class<?> value();

    /**
     * 是否为扩展，默认 {@code false}。
     */
    boolean expand() default false;
}
