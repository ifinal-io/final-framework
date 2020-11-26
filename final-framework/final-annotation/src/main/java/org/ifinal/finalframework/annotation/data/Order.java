package org.ifinal.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@org.springframework.core.annotation.Order
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    @AliasFor(annotation = org.springframework.core.annotation.Order.class)
    int value();
}
