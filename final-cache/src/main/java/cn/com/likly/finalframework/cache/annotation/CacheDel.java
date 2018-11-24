package cn.com.likly.finalframework.cache.annotation;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @since 1.0
 * @see cn.com.likly.finalframework.cache.Cache#del(Object)
 * @see cn.com.likly.finalframework.cache.Cache#hdel(Object, Object)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDel {
    String key();

    String field() default "";

    String condition() default "";


}
