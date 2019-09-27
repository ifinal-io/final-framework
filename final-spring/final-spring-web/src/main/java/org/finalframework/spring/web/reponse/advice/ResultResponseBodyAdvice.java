package org.finalframework.spring.web.reponse.advice;

import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:22:08
 * @since 1.0
 */
@Order(RestAdviceOrdered.RESULT_PRECEDENCE)
@RestControllerAdvice
public class ResultResponseBodyAdvice extends RestMethodParameterFilter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return matches(returnType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final Result result = buildResult(body);
        String trace = MDC.get("trace");
        result.setTrace(trace);
        return result;
    }

    @NonNull
    private Result buildResult(Object body) {
        if (body == null) return R.success();

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        return R.success(body);
    }
}
