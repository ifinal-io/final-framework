package org.finalframework.spring.web.response.advice;

import com.github.pagehelper.Page;
import org.finalframework.spring.annotation.factory.SpringResponseBodyAdvice;
import org.finalframework.spring.web.converter.Enums2EnumBeansConverter;
import org.finalframework.spring.web.converter.Page2PageConverter;
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
@SpringResponseBodyAdvice
@Order(RestAdviceOrdered.DEFAULT_PRECEDENCE)
@RestControllerAdvice
public class PageResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    private static final Page2PageConverter page2PageConverter = new Page2PageConverter();

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body instanceof Page ? page2PageConverter.convert((Page<?>) body) : body;
    }

}


