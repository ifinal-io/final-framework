package org.finalframework.spring.web.autoconfigure;


import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 11:08:13
 * @since 1.0
 */
@ConfigurationProperties(ExceptionHandlerProperties.EXCEPTION_PROPERTIES)
public class ExceptionHandlerProperties {
    protected static final String EXCEPTION_PROPERTIES = "final.exception";

    private Class<? extends GlobalExceptionHandler> globalExceptionHandler = ResultGlobalResultExceptionHandler.class;

    public Class<? extends GlobalExceptionHandler> getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public void setGlobalExceptionHandler(Class<? extends GlobalExceptionHandler> globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }
}

