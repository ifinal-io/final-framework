package org.finalframework.spring.web.reponse.advice;

import org.finalframework.data.response.Responsible;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:33:39
 * @since 1.0
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class ResponsibleResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    private boolean syncStatus;

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (syncStatus && body instanceof Responsible) {
            final HttpStatus httpStatus = HttpStatus.resolve(((Responsible) body).getStatus());
            if (httpStatus != null) {
                response.setStatusCode(httpStatus);
            }
        }

        return body;
    }
}
