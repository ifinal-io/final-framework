package com.ilikly.finalframework.spring.aop.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 09:57:10
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface OperationValue {
    String value();
}
