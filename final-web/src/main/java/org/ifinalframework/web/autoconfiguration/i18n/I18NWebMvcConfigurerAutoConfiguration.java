/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.web.autoconfiguration.i18n;

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

import org.ifinalframework.context.util.Messages;
import org.ifinalframework.web.i18n.I18NLocaleResolver;

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

    public I18NWebMvcConfigurerAutoConfiguration(final I18NProperties properties, final MessageSource messageSource) {

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

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor());
    }

    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(properties.getParamName());
        return interceptor;
    }

}

