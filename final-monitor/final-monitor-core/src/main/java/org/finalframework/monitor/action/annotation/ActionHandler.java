package org.finalframework.monitor.action.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:55:45
 * @see org.finalframework.monitor.action.ActionContextHandler
 * @since 1.0
 */
@Documented
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionHandler {
    int[] value() default {};
}
