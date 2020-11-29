package org.ifinal.finalframework.boot.autoconfigure.web.i18n;


import org.ifinal.finalframework.context.util.Messages;
import org.ifinal.finalframework.web.i18n.I18NLocaleResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(LocaleResolver.class)
@EnableConfigurationProperties(I18NProperties.class)
public class I18NWebMvcConfigurerAutoConfiguration implements WebMvcConfigurer {

    private final I18NProperties properties;

    public I18NWebMvcConfigurerAutoConfiguration(I18NProperties properties, MessageSource messageSource) {
        this.properties = properties;
        Messages.setMessageSource(messageSource);
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

