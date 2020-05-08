package org.finalframework.spring.web.configurer;

import org.finalframework.spring.annotation.factory.SpringWebMvcConfigurer;
import org.finalframework.spring.web.autoconfiguration.CorsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:29
 * @since 1.0
 */
@SpringWebMvcConfigurer
@EnableConfigurationProperties(CorsProperties.class)
public class CorsWebMvcConfigurerConfigurer implements WebMvcConfigurer {
    private final CorsProperties corsProperties;

    public CorsWebMvcConfigurerConfigurer(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(corsProperties.getMapping())
                .allowCredentials(Boolean.TRUE.equals(corsProperties.getAllowCredentials()))
                .allowedOrigins(corsProperties.getAllowedOrigins())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders());
    }


}
