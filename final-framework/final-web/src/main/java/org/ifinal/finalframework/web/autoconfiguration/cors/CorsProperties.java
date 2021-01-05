package org.ifinal.finalframework.web.autoconfiguration.cors;

import java.io.Serializable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = CorsProperties.PREFIX)
public class CorsProperties implements Serializable {

    public static final String PREFIX = "final.web.cors";

    private static final long serialVersionUID = 8336511916409326596L;

    /**
     * @see CorsRegistration#pathPattern
     */
    private String mapping;

    /**
     * @see CorsRegistration#allowCredentials(boolean)
     */
    private Boolean allowCredentials;

    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedMethods;

    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedHeaders;

    /**
     * @see CorsRegistration#allowedOrigins(String...)
     */
    private String[] allowedOrigins;

}

