package com.ilikly.finalframework.spring.coding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the target type is a spring application listener
 * @author likly
 * @version 1.0
 * @date 2018-12-25 22:15:37
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationEventListener {
}
