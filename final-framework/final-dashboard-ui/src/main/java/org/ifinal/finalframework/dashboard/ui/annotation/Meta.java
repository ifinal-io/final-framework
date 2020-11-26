package org.ifinal.finalframework.dashboard.ui.annotation;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Repeatable(Meta.Metas.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Meta {
    String name() default "";

    String content() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @interface Metas {
        Meta[] value() default {};
    }

}
