package org.ifinal.finalframework.aop.annotation;

import java.lang.annotation.*;

/**
 * 属性
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationAttribute {
    /**
     * 属性名称
     */
    String name();

    /**
     * 属性值，可为SPEL表达式
     */
    String value();
}
