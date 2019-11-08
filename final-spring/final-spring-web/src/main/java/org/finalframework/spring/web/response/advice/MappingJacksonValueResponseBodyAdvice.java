package org.finalframework.spring.web.response.advice;


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
            Object value = ((MappingJacksonValue) body).getValue();
            if (value instanceof Result) {
                ((Result) value).setView(((MappingJacksonValue) body).getSerializationView());
                return value;
            }
        }
        return body;
    }
}

