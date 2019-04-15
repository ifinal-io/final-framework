package org.finalframework.spring.web.exception;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.data.exception.GlobalExceptionHandler;
import org.finalframework.json.JsonException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandlerConfigurer {
    @Setter
    private GlobalExceptionHandler<?> globalExceptionHandler;

    @Bean
    @ConditionalOnClass(JsonException.class)
    public JsonResultExceptionHandler jsonResultExceptionHandler() {
        return new JsonResultExceptionHandler();
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
