package org.finalframework.spring.web.autoconfigure;


import org.finalframework.spring.web.exception.RestGlobalExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-29 21:53:04
 * @since 1.0
 */
@ConfigurationProperties(prefix = RestExceptionHandlerProperties.PROPERTIES_PREFIX)
public class RestExceptionHandlerProperties {
    static final String PROPERTIES_PREFIX = "final.exception";

    private String logger = RestGlobalExceptionHandler.class.getName();

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }
}
