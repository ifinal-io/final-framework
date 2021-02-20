package org.ifinal.finalframework.web.response.advice.result;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ifinal.finalframework.annotation.core.result.Result;
import org.ifinal.finalframework.context.converter.result.Object2ResultConverter;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.web.interceptor.DurationHandlerInterceptor;
import org.ifinal.finalframework.web.interceptor.TraceHandlerInterceptor;
import org.ifinal.finalframework.web.response.advice.RestResponseBodyAdvice;

import java.time.Duration;

/**
 * @author likly
 * @version 1.0.0
 * @see Object2ResultConverter
 * @since 1.0.0
 */
@Order(1000)
@RestControllerAdvice
public class ResultResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    private static final Object2ResultConverter object2ResultConverter = new Object2ResultConverter();

    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter returnType,
        final MediaType selectedContentType,
        final Class<? extends HttpMessageConverter<?>> selectedConverterType,
        final ServerHttpRequest request, final ServerHttpResponse response) {

        final Result<?> result = object2ResultConverter.convert(body);
        if (result == null) {
            return null;
        }
        // set address
        result.setAddress(request.getLocalAddress().getAddress().getHostName());
        result.setIp(String.format("%s:%d", request.getLocalAddress().getAddress().getHostAddress(),
            request.getLocalAddress().getPort()));
        // set locale
        result.setLocale(LocaleContextHolder.getLocale());
        // set timeZone
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        // set operator
        result.setOperator(UserContextHolder.getUser());

        if (request instanceof ServletServerHttpRequest) {
            Long durationStart = (Long) ((ServletServerHttpRequest) request).getServletRequest()
                .getAttribute(DurationHandlerInterceptor.DURATION_START_ATTRIBUTE);
            if (durationStart != null) {
                result.setDuration(Duration.ofMillis(System.currentTimeMillis() - durationStart));
            }
            String trace = (String) ((ServletServerHttpRequest) request).getServletRequest()
                .getAttribute(TraceHandlerInterceptor.TRACE_ATTRIBUTE);
            result.setTrace(trace);
        }
        return result;
    }

}
