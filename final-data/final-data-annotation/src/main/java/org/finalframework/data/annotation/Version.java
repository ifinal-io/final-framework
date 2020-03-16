package org.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-22 14:34:21
 * @since 1.0
 */
@Column
@ReadOnly
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Version
public @interface Version {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
