package org.ifinal.finalframework.web.response.advice;


import org.ifinal.finalframework.web.converter.List2ResultConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(RestAdviceOrdered.DEFAULT_PRECEDENCE)
@RestControllerAdvice
public class ListResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    private final List2ResultConverter list2ResultConverter = new List2ResultConverter();

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof List) {
            return list2ResultConverter.convert((List) body);
        }
        return body;
    }
}

