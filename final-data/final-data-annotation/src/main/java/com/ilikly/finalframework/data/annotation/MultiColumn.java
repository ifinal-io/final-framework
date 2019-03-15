package com.ilikly.finalframework.data.annotation;

import com.ilikly.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.annotation.Reference;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
@Reference
@Deprecated
public @interface MultiColumn {

    @AliasFor(value = "name")
    String value() default "";

    @AliasFor(value = "value")
    String name() default "";

    //    @AliasFor(value = "properties", annotation = ReferenceColumn.class)
    String[] properties();

    String delimiter() default ":";

    //    @AliasFor(value = "mode", annotation = ReferenceColumn.class)
    ReferenceMode mode() default ReferenceMode.SIMPLE;

    //    @AliasFor(value = "unique", annotation = ReferenceColumn.class)
    boolean unique() default false;

    //    @AliasFor(value = "nonnull", annotation = ReferenceColumn.class)
    boolean nonnull() default false;

    //    @AliasFor(value = "insertable", annotation = ReferenceColumn.class)
    boolean insertable() default true;

    //    @AliasFor(value = "updatable", annotation = ReferenceColumn.class)
    boolean updatable() default true;

    //    @AliasFor(value = "selectable", annotation = ReferenceColumn.class)
    boolean selectable() default true;

}
