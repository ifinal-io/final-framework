package org.ifinal.finalframework.boot.autoconfigure.web.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@ConditionalOnClass(WebMvcConfigurer.class)
@EnableConfigurationProperties(CorsProperties.class)
public class CorsWebMvcConfigurerAutoConfiguration implements WebMvcConfigurer {
    private final CorsProperties corsProperties;

    public CorsWebMvcConfigurerAutoConfiguration(final CorsProperties corsProperties) {

        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {

        registry.addMapping(corsProperties.getMapping())
                .allowCredentials(Boolean.TRUE.equals(corsProperties.getAllowCredentials()))
                .allowedOrigins(corsProperties.getAllowedOrigins())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders());
    }


}
