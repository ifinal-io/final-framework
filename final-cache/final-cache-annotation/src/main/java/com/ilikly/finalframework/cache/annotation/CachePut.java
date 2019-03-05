package com.ilikly.finalframework.cache.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @since 1.0
 */
@CacheAnnotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String result() default "#result";

    String delimiter() default ":";

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
