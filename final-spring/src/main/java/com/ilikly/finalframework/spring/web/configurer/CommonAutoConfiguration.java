package com.ilikly.finalframework.spring.web.configurer;

import com.ilikly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:15:24
 * @since 1.0
 */
@AutoConfiguration
public class CommonAutoConfiguration {
    @Bean
    public CorsConfigurer corsConfigurer() {
        return new CorsConfigurer();
    }

    @Bean
    public SpringConfigResource springConfigResource() {
        return new SpringConfigResource();
    }
}
