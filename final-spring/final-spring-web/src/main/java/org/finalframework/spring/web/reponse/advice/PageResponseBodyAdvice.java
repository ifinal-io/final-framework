package org.finalframework.spring.web.reponse.advice;

import com.github.pagehelper.Page;
import org.finalframework.spring.web.reponse.converter.Page2PageConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:29:47
 * @since 1.0
 */
@Order(0)
@RestControllerAdvice
public class PageResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    private Page2PageConverter page2PageConverter = new Page2PageConverter();

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof Page) {
            return page2PageConverter.convert((Page) body);
        }

        return body;
    }

}
