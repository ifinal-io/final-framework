package org.ifinal.finalframework.annotation.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import org.springframework.core.annotation.AliasFor;

/**
 * 缓存，为目标方法增加缓存能力。
 * <ol>
 * <li>在执行之前，优先从 {@link #key()} 和 {@link #field()} 所描述的缓存区域里读取
 * {@link Cache#get(Object, Object, Type, Class)}，如果命中，则直接返回。</li>
 * <li>在执行之后，将方法的执行结果写到 {@link #key()} 和 {@link #field()} 所描述的缓存区域里，
 * {@link Cache#set(Object, Object, Object, Long, TimeUnit, Class)}。</li>
 * </ol>
 *
 * @author likly
 * @version 1.0.0
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @see Cache#get(Object, Object, Type, Class)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     * 缓存区
     *
     * @return key
     */
    String[] key();

    /**
     * 缓存域
     *
     * @return field
     */
    String[] field() default {};

    /**
     * 缓存区域的分隔符
     *
     * @return delimiter
     */
    String delimiter() default ":";

    /**
     * 缓存条件
     *
     * @return condition
     * @see #when()
     */
    @AliasFor("when")
    String condition() default "";

    /**
     * 缓存条件
     *
     * @return when
     * @see #condition()
     */
    @AliasFor("condition")
    String when() default "";

    /**
     * 过期时间
     *
     * @return expire
     */
    String expire() default "";

    /**
     * 有效时间
     *
     * @return ttl
     */
    long ttl() default -1L;

    /**
     * 有效时间单位
     *
     * @return timeunit
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

}
