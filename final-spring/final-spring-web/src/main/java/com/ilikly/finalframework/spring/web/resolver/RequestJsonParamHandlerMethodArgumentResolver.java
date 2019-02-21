package com.ilikly.finalframework.spring.web.resolver;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.exception.BadRequestException;
import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.spring.web.resolver.annotation.RequestJsonParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-31 11:22:47
 * @since 1.0
 */
public class RequestJsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestJsonParam requestJsonParam = parameter.getParameterAnnotation(RequestJsonParam.class);
        if (Assert.isNull(requestJsonParam)) {
            // it can not enter
            throw new NullPointerException("requestJsonParam annotation is null");
        }
        final String parameterName = getParameterName(requestJsonParam, parameter);
        String value = webRequest.getParameter(parameterName);
        if (Assert.isEmpty(value) && !ValueConstants.DEFAULT_NONE.equals(requestJsonParam.defaultValue())) {
            value = requestJsonParam.defaultValue();
        }
        if (Assert.isEmpty(value) && requestJsonParam.required()) {
            throw new BadRequestException("parameter %s is required", parameterName);
        }

        if(Assert.isEmpty(value)) return null;

        Type parameterType = parameter.getGenericParameterType();
        return Json.parse(value, parameterType);
    }

    private String getParameterName(RequestJsonParam requestJsonParam, MethodParameter parameter) {
        return Assert.isEmpty(requestJsonParam.value()) ? parameter.getParameterName() : requestJsonParam.value().trim();
    }
}
