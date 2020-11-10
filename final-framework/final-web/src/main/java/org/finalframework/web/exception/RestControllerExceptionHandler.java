package org.finalframework.web.exception;


import org.finalframework.context.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@RestControllerAdvice
@ConditionalOnBean(GlobalExceptionHandler.class)
public class RestControllerExceptionHandler {

    private final GlobalExceptionHandler<?> globalExceptionHandler;

    public RestControllerExceptionHandler(GlobalExceptionHandler<?> globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Object handlerException(Throwable throwable) throws Throwable {
        if (globalExceptionHandler != null) {
            return globalExceptionHandler.handle(throwable);
        }
        throw throwable;
    }
}
