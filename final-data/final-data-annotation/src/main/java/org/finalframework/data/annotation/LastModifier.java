package org.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 最后更新时间
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@Index(Integer.MAX_VALUE - 99)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModifier {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
