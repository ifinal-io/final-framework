package org.finalframework.web.response.advice;


import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import org.finalframework.auto.spring.factory.annotation.SpringResponseBodyAdvice;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 15:56:23
 * @since 1.0
 */
@Order(RestAdviceOrdered.JSON_VIEW_PRECEDENCE)
@RestControllerAdvice
@SpringResponseBodyAdvice
public class JsonViewResponseBodyAdvice extends RestResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && returnType.hasMethodAnnotation(JsonView.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        Class<?> view = getJsonView(returnType);

        if (body instanceof Page) {
            return null;
        } else {
            return new JsonViewValue(body, view);
        }

    }

    private Class<?> getJsonView(@NonNull MethodParameter returnType) {
        JsonView ann = Objects.requireNonNull(returnType.getMethodAnnotation(JsonView.class), "No JsonView annotation");
        Class<?>[] classes = ann.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for response body advice with exactly 1 class argument: " + returnType);
        }

        return classes[0];
    }
}

