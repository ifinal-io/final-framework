package org.ifinal.finalframework.dashboard.ui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Meta(name = "keywords")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Keywords {

    @AliasFor(annotation = Meta.class, attribute = "content")
    String value();

}
