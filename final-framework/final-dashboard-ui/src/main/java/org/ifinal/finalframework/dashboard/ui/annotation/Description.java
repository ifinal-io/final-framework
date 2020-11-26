package org.ifinal.finalframework.dashboard.ui.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Meta(name = "description")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Description {
    @AliasFor(annotation = Meta.class, attribute = "content")
    String value();
}
