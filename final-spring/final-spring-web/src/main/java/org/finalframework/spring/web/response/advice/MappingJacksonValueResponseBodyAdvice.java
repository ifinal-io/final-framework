package org.finalframework.spring.web.response.advice;


import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringResponseBodyAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-07 18:42:36
 * @since 1.0
 */
@SpringResponseBodyAdvice
public class MappingJacksonValueResponseBodyAdvice extends RestMethodParameterFilter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return matches(returnType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof MappingJacksonValue) {
            MappingJacksonValue container = (MappingJacksonValue) body;
            Object value = container.getValue();
            Class<?> view = container.getSerializationView();
            if (value instanceof Result) {
                Result result = (Result) value;
                result.setView(view);
                result.setData(new JsonViewValue(result.getData(), view));
                return result;
            } else {
                return new JsonViewValue(value, view);
            }
        }
        return body;
    }
}

