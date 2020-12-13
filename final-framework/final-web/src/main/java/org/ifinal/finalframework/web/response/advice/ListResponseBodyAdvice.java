package org.ifinal.finalframework.web.response.advice;

import java.util.List;
import org.ifinal.finalframework.web.converter.List2ResultConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(0)
@RestControllerAdvice
public class ListResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    private final List2ResultConverter<?> list2ResultConverter = new List2ResultConverter<>();

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(final Object body, final @NonNull MethodParameter returnType,
        final @NonNull MediaType selectedContentType,
        final @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
        final @NonNull ServerHttpRequest request, final @NonNull ServerHttpResponse response) {

        if (body instanceof List) {
            return list2ResultConverter.convert((List) body);
        }
        return body;
    }

}

