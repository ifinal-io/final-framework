package org.finalframework.spring.web.configurer;

import org.finalframework.auto.spring.factory.annotation.SpringWebMvcConfigurer;
import org.finalframework.spring.web.converter.EnumConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 10:47:25
 * @since 1.0
 */
@SpringWebMvcConfigurer
public class EnumConverterFactoryWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public EnumConverterFactory enumConverterFactory() {
        return new EnumConverterFactory();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(enumConverterFactory());
    }
}
