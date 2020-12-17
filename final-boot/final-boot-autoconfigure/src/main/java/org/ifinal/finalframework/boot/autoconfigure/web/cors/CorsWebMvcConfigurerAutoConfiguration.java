package org.ifinal.finalframework.boot.autoconfigure.web.cors;

import java.util.Objects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
@EnableConfigurationProperties(CorsProperties.class)
public class CorsWebMvcConfigurerAutoConfiguration implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public CorsWebMvcConfigurerAutoConfiguration(final CorsProperties corsProperties) {

        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {

        if (Objects.isNull(corsProperties.getMapping())) {
            return;
        }

        CorsRegistration registration = registry.addMapping(corsProperties.getMapping());

        if (Objects.nonNull(corsProperties.getAllowCredentials())) {
            registration.allowCredentials(corsProperties.getAllowCredentials());
        }

        if (Objects.nonNull(corsProperties.getAllowedOrigins())) {
            registration.allowedOrigins(corsProperties.getAllowedOrigins());
        }

        if (Objects.nonNull(corsProperties.getAllowedHeaders())) {
            registration.allowedHeaders(corsProperties.getAllowedHeaders());
        }

        if (Objects.nonNull(corsProperties.getAllowedMethods())) {
            registration.allowedMethods(corsProperties.getAllowedMethods());
        }

    }

}
