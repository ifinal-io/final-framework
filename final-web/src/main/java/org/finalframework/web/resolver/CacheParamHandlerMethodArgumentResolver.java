package org.finalframework.web.resolver;


import org.finalframework.auto.spring.factory.annotation.SpringArgumentResolver;
import org.finalframework.cache.annotation.CacheValue;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 13:25:30
 * @since 1.0
 */
@SpringArgumentResolver
public class CacheParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CacheValue.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }

}
