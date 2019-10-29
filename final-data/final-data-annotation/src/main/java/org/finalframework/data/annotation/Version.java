package org.finalframework.data.annotation;

import org.finalframework.data.annotation.enums.PersistentType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-22 14:34:21
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Version
public @interface Version {
    @AliasFor("name")
    String value() default "";

    String table() default "";

    /**
     * 即将废弃，因 {@link AliasFor}注解并不支持编译时处理
     *
     * @see #value()
     */
    @Deprecated
    @AliasFor("value")
    String name() default "";

    PersistentType persistentType() default PersistentType.AUTO;

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default false;

    boolean updatable() default false;

    boolean selectable() default true;

    boolean placeholder() default true;
}
