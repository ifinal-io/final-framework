package org.ifinal.finalframework.web.response.advice;


import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @see ResultResponseBodyAdvice
 * @since 1.0.0
 */
public abstract class RestResponseBodyAdvice<T> implements ResponseBodyAdvice<T> {

    private static final RestMethodParameterFilter restMethodParameterFilter = new RestMethodParameterFilter();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return restMethodParameterFilter.matches(methodParameter);
    }
}

