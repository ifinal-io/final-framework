package org.finalframework.data.autoconfigure;


import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-29 21:53:04
 * @since 1.0
 */
@ConfigurationProperties(prefix = ResultExceptionHandlerProperties.PROPERTIES_PREFIX)
public class ResultExceptionHandlerProperties {
    static final String PROPERTIES_PREFIX = "final.exception";

    private String logger = ResultGlobalResultExceptionHandler.class.getName();

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }
}
