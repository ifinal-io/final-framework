package org.ifinal.finalframework.web.exception;

import org.ifinal.finalframework.context.exception.UnCatchException;
import org.ifinal.finalframework.context.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestControllerAdvice
@ConditionalOnBean(GlobalExceptionHandler.class)
public class RestControllerExceptionHandler {

    private final GlobalExceptionHandler<?> globalExceptionHandler;

    public RestControllerExceptionHandler(final GlobalExceptionHandler<?> globalExceptionHandler) {

        this.globalExceptionHandler = globalExceptionHandler;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Object handlerException(final Throwable throwable) {

        if (globalExceptionHandler != null) {
            return globalExceptionHandler.handle(throwable);
        }
        throw new UnCatchException(throwable);
    }

}
