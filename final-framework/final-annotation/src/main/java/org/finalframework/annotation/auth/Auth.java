package org.finalframework.annotation.auth;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-12 14:01:44
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    Class<? extends Annotation> value() default Auth.class;
}
