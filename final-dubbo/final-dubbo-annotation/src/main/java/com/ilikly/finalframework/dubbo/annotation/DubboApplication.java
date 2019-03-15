package com.ilikly.finalframework.dubbo.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-26 11:35:51
 * @since 1.0
 */
@Target({})
@Retention(RetentionPolicy.CLASS)
public @interface DubboApplication {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String[] owners() default {};
}
