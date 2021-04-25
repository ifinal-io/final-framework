package org.ifinal.finalframework.web.response.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.ifinal.finalframework.web.response.advice.result.ResultResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @see ResultResponseBodyAdvice
 * @since 1.0.0
 */
public interface RestResponseBodyAdvice<T> extends ResponseBodyAdvice<T> {

    @Override
    default boolean supports(final @NonNull MethodParameter methodParameter,
        final @NonNull Class<? extends HttpMessageConverter<?>> converterType) {

        return RestMethodParameterFilter.INSTANCE.matches(methodParameter);
    }

}

