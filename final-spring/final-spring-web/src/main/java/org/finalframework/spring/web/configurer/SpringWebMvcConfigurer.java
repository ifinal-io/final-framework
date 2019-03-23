package org.finalframework.spring.web.configurer;

import org.finalframework.spring.web.converter.EnumConverterFactory;
import org.finalframework.spring.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 10:47:25
 * @since 1.0
 */
@Configuration
public class SpringWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(0, new RequestJsonParamHandlerMethodArgumentResolver());
    }

    @Bean
    public EnumConverterFactory enumConverterFactory() {
        return new EnumConverterFactory();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(enumConverterFactory());
    }
}
