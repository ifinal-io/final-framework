package org.finalframework.spring.web.reponse.advice;

import org.finalframework.core.Assert;
import org.finalframework.data.entity.enums.IEnum;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:31:45
 * @since 1.0
 */
@Order(0)
@RestControllerAdvice
public class EnumsResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (supports(body)) {
            if (body instanceof Collection) {
                Collection<IEnum> enums = (Collection<IEnum>) body;
                return enums.stream().map(this::buildEnumMap).collect(Collectors.toList());
            } else if (body.getClass().isArray()) {
                IEnum[] enums = (IEnum[]) body;
                return Arrays.stream(enums).map(this::buildEnumMap).collect(Collectors.toList());
            }
        }

        return body;
    }

    private boolean supports(Object body) {
        if (Assert.isNull(body)) return false;

        if (body.getClass().isArray()) {
            if (Assert.isEmpty(body)) return false;
            return ((Object[]) body)[0] instanceof IEnum;
        }

        if (body instanceof List) {
            if (Assert.isEmpty(body)) return false;
            return ((List) body).get(0) instanceof IEnum;
        }

        if (body instanceof Set) {
            if (Assert.isEmpty(body)) return false;
            return ((Set) body).toArray()[0] instanceof IEnum;
        }

        return false;
    }

    private Map<String, Object> buildEnumMap(IEnum e) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", e.getCode());
        result.put("name", e.getDescription());
        return result;
    }
}


