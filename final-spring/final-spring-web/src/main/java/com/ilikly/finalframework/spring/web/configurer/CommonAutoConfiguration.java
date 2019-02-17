package com.ilikly.finalframework.spring.web.configurer;

import com.ilikly.finalframework.spring.coding.AutoConfiguration;
import com.ilikly.finalframework.spring.web.converter.String2DateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:15:24
 * @since 1.0
 */
@Configuration
@AutoConfiguration
public class CommonAutoConfiguration {

    @Bean
    public CorsConfigurer corsConfigurer() {
        return new CorsConfigurer();
    }

    @Bean
    public SpringResourceConfigurer springConfigResource() {
        return new SpringResourceConfigurer();
    }

    @Bean
    public SpringWebMvcConfigurer springWebMvcConfigurer(){
        return new SpringWebMvcConfigurer();
    }

    @Bean
    public String2DateConverter string2DateConverter(){
        return new String2DateConverter();
    }
}
