package org.finalframework.spring.web.exception;

import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@Configuration
@RestControllerAdvice
public class RestExceptionHandlerConfigurer {
    private GlobalExceptionHandler<?> globalExceptionHandler;

    public GlobalExceptionHandler<?> getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public void setGlobalExceptionHandler(GlobalExceptionHandler<?> globalExceptionHandler) {
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
