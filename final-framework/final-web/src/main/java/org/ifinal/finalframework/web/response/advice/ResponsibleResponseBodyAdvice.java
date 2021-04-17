package org.ifinal.finalframework.web.response.advice;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ifinal.finalframework.core.result.Responsible;

/**
 * @author likly
 * @version 1.0.0
 * @see Responsible
 * @since 1.0.0
 */
@Order
@RestControllerAdvice
public class ResponsibleResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    /**
     * 是否同步Response状态
     */
    private boolean syncStatus = true;

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(final boolean syncStatus) {

        this.syncStatus = syncStatus;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(final @Nullable Object body, final @NonNull MethodParameter returnType,
        final MediaType selectedContentType,
        final Class<? extends HttpMessageConverter<?>> selectedConverterType,
        final ServerHttpRequest request,
        final ServerHttpResponse response) {

        if (syncStatus && body instanceof Responsible) {
            final HttpStatus httpStatus = HttpStatus.resolve(((Responsible) body).getStatus());
            if (httpStatus != null) {
                response.setStatusCode(httpStatus);
            }
        }

        return body;
    }

}
