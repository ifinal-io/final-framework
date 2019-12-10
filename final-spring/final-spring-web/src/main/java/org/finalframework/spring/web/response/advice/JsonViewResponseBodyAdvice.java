package org.finalframework.spring.web.response.advice;


import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.data.result.Page;
import org.finalframework.json.jackson.view.PageJsonViewValue;
import org.finalframework.spring.annotation.factory.SpringResponseBodyAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 15:56:23
 * @since 1.0
 */
@Order(RestAdviceOrdered.JSON_VIEW_PRECEDENCE)
@RestControllerAdvice
@SpringResponseBodyAdvice
public class JsonViewResponseBodyAdvice extends RestMethodParameterFilter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return matches(returnType) && returnType.hasMethodAnnotation(JsonView.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) return null;
        Class<?> view = getJsonView(returnType);

        if (body instanceof Page) {
            return new PageJsonViewValue<>((Page<?>) body, view);
        } else {
            return new JsonViewValue<>(body, view);
        }


    }

    private Class<?> getJsonView(MethodParameter returnType) {
        JsonView ann = returnType.getMethodAnnotation(JsonView.class);
        Assert.state(ann != null, "No JsonView annotation");

        Class<?>[] classes = ann.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for response body advice with exactly 1 class argument: " + returnType);
        }

        return classes[0];
    }
}

