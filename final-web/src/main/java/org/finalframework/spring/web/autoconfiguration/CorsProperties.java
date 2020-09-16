package org.finalframework.spring.web.autoconfiguration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-05 22:54:58
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = CorsProperties.PREFIX)
public class CorsProperties {

    public static final String PREFIX = "final.web.cors";
    /**
     * @see CorsRegistration#pathPattern
     */
    private String mapping = "/**";
    /**
     * @see CorsRegistration#allowCredentials(boolean)
     */
    private Boolean allowCredentials = true;
    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedMethods = {"*"};

    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedHeaders = {"*"};

    /**
     * @see CorsRegistration#allowedOrigins(String...)
     */
    private String[] allowedOrigins = {"*"};
}

