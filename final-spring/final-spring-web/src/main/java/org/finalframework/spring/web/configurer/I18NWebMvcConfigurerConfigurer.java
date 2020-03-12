package org.finalframework.spring.web.configurer;


import org.finalframework.data.util.Messages;
import org.finalframework.spring.annotation.factory.SpringWebMvcConfigurer;
import org.finalframework.spring.web.i18n.I18NLocaleResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-12 13:56:07
 * @since 1.0
 */
@SpringWebMvcConfigurer
@EnableConfigurationProperties(I18NProperties.class)
public class I18NWebMvcConfigurerConfigurer implements WebMvcConfigurer {

    private final I18NProperties properties;

    public I18NWebMvcConfigurerConfigurer(I18NProperties properties, MessageSource messageSource) {
        this.properties = properties;
        new Messages(messageSource);
    }

    @Bean(name = "localeResolver")
    @ConditionalOnMissingBean(LocaleResolver.class)
    public I18NLocaleResolver localeResolver() {
        I18NLocaleResolver localeResolver = new I18NLocaleResolver();
        localeResolver.setDefaultLocale(properties.getDefaultLocale());
        localeResolver.setSupportedLocales(properties.getSupportedLocales());

        localeResolver.setParamName(properties.getParamName());
        localeResolver.setHeaderName(properties.getHeaderName());

        localeResolver.setCookieName(properties.getCookieName());
        localeResolver.setCookieMaxAge(properties.getCookieMaxAge());
        localeResolver.setCookieDomain(properties.getCookieDomain());
        localeResolver.setCookiePath(properties.getCookiePath());
        return localeResolver;
    }

    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(properties.getParamName());
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

