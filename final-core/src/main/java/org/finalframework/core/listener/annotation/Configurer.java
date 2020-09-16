package org.finalframework.core.listener.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-05-05 10:10:59
 * @since 1.0
 */
@Component
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Configurer {
    Class<?> value();
}
