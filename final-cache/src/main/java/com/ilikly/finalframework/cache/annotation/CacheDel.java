package com.ilikly.finalframework.cache.annotation;

import com.ilikly.finalframework.cache.Cache;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#del(Object)
 * @see Cache#hdel(Object, Object)
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDel {

    String keyFormat() default "";

    String[] keys();

    String fieldFormat() default "";

    String[] fields() default {};

    String condition() default "";


}
