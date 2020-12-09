package org.ifinal.finalframework.sharding.annotation;

import org.ifinal.finalframework.data.annotation.Table;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Table
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BroadcastTable {
    @AliasFor(annotation = Table.class, value = "value")
    String value() default "";
}
