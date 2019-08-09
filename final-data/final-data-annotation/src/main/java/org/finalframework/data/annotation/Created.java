package org.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedDate;

import java.lang.annotation.*;

/**
 * 创建时间
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see LastModified
 * @since 1.0
 */
@Index(Integer.MAX_VALUE - 100)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CreatedDate
public @interface Created {
    String table() default "";

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default false;

    boolean updatable() default false;

    boolean selectable() default true;

    boolean placeholder() default true;
}
