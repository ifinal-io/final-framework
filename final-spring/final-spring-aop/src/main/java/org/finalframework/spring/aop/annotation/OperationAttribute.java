package org.finalframework.spring.aop.annotation;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:00:29
 * @since 1.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationAttribute {
    String name();

    String value();
}
