package org.finalframework.spring.web.response.advice;

import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.result.*;
import org.finalframework.spring.annotation.factory.SpringResponseBodyAdvice;
import org.finalframework.spring.web.interceptor.DurationHandlerInterceptor;
import org.finalframework.spring.web.interceptor.TraceHandlerInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:22:08
 * @since 1.0
 */
@Order(RestAdviceOrdered.RESULT_PRECEDENCE)
@RestControllerAdvice
@SpringResponseBodyAdvice
public class ResultResponseBodyAdvice extends RestMethodParameterFilter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return matches(returnType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final Result result = buildResult(body);
        result.setView(getJsonView(returnType));
        if (request instanceof ServletServerHttpRequest) {
            Long durationStart = (Long) ((ServletServerHttpRequest) request).getServletRequest().getAttribute(DurationHandlerInterceptor.DURATION_START_ATTRIBUTE);
            if (durationStart != null) {
                result.setDuration(System.currentTimeMillis() - durationStart);
            }
            String trace = (String) ((ServletServerHttpRequest) request).getServletRequest().getAttribute(TraceHandlerInterceptor.TRACE_ATTRIBUTE);
            result.setTrace(trace);
        }

        return result;
    }

    @NonNull
    private Result buildResult(Object body) {
        if (body == null) return R.success();

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        if (body instanceof Page) {
            Page page = (Page) body;
            Result<List> result = R.success(page.getResult());
            result.setPage(page.toPageInfo());
            return result;
        } else if (body instanceof JsonViewPageValue) {
            JsonViewPageValue page = (JsonViewPageValue) body;
            Result<JsonViewValue> result = R.success(page.getValue());
            result.setPage(page.toPageInfo());
            return result;
        }

        return R.success(body);
    }

    private Class<?> getJsonView(MethodParameter returnType) {
        JsonView ann = returnType.getMethodAnnotation(JsonView.class);

        if (ann == null) return null;

        Class<?>[] classes = ann.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for response body advice with exactly 1 class argument: " + returnType);
        }

        return classes[0];
    }
}
