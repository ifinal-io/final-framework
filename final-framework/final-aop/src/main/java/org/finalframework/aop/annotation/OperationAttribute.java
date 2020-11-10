package org.finalframework.aop.annotation;

import java.lang.annotation.*;

/**
 * 属性
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:00:29
 * @since 1.0
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
