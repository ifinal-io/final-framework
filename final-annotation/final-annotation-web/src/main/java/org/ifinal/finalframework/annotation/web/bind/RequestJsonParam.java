package org.ifinal.finalframework.annotation.web.bind;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A ext {@link Annotation} for {@link HandlerMethodArgumentResolver} like {@link RequestParam},{@link RequestBody}.
 *
 * @author likly
 * @version 1.0.0
 * @see RequestParam
 * @see RequestBody
 * @see HandlerMethodArgumentResolver
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;

}
