package org.finalframework.auto.spring.factory.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the target type is a spring application listener
 *
 * @author likly
 * @version 1.0
 * @date 2018-12-25 22:15:37
 * @see SpringApplication#getListeners()
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(ApplicationListener.class)
public @interface SpringApplicationListener {
}
