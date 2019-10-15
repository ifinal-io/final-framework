package org.finalframework.data.annotation;

import org.finalframework.data.annotation.enums.PersistentType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
public @interface Column {

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

    boolean insertable() default true;

    boolean updatable() default true;

    boolean selectable() default true;

    boolean placeholder() default true;

}
