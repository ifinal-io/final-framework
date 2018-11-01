package cn.com.likly.finalframework.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HCacheSet {
    String key();

    String field();

    String condition() default "";

    String expired() default "";

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
