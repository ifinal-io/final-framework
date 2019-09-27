package org.finalframework.spring.web.reponse.advice;

import org.finalframework.spring.web.converter.Enums2EnumBeansConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:31:45
 * @see Enums2EnumBeansConverter
 * @since 1.0
 */
@Order(RestAdviceOrdered.DEFAULT_PRECEDENCE)
@RestControllerAdvice
public class EnumsResponseBodyAdvice extends RestMethodParameterFilter implements ResponseBodyAdvice<Object> {

    private static final Enums2EnumBeansConverter enums2EnumBeansConverter = new Enums2EnumBeansConverter();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return matches(returnType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return enums2EnumBeansConverter.matches(body) ? enums2EnumBeansConverter.convert(body) : body;
    }

}


