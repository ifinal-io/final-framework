package org.ifinal.finalframework.web.resolver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;
import org.ifinal.finalframework.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

/**
 * 被{@link RequestJsonParam}注解标记的{@link Parameter}元素，会将表单中的的{@link RequestJsonParam#name()}所对就的值使用Json 反序列化为目标所声明的类型。
 *
 * @author likly
 * @version 1.0.0
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see RequestJsonParamHandlerMethodArgumentResolver
 * @since 1.0.0
 */
@Deprecated
@org.ifinal.finalframework.annotation.web.bind.RequestJsonParam
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonParam {

    @AliasFor(annotation = org.ifinal.finalframework.annotation.web.bind.RequestJsonParam.class)
    String value() default "";

    @AliasFor(annotation = org.ifinal.finalframework.annotation.web.bind.RequestJsonParam.class)
    String name() default "";

    @AliasFor(annotation = org.ifinal.finalframework.annotation.web.bind.RequestJsonParam.class)
    boolean required() default true;

    @AliasFor(annotation = org.ifinal.finalframework.annotation.web.bind.RequestJsonParam.class)
    String defaultValue() default ValueConstants.DEFAULT_NONE;

}
