package org.finalframework.core.listener.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-05-05 10:05:28
 * @since 1.0
 */
@Documented
@Component
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    Class<?> value();
}

