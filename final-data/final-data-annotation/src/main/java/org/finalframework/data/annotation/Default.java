package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 22:59:10
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default {
}
