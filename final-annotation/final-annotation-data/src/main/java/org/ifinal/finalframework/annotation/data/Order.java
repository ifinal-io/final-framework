package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@org.springframework.core.annotation.Order
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    @AliasFor(annotation = org.springframework.core.annotation.Order.class)
    int value();

}
