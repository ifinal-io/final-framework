package org.finalframework.dashboard.ui.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/21 17:13:46
 * @since 1.0
 */
@Meta(name = "keywords")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Keywords {
    @AliasFor(annotation = Meta.class, attribute = "content")
    String value();
}
