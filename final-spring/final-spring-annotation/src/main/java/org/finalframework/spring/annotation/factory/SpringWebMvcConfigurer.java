package org.finalframework.spring.annotation.factory;

import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-07 14:58:19
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(value = WebMvcConfigurer.class,expand = true)
public @interface SpringWebMvcConfigurer {
}
