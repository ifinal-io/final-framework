/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.boot.autoconfigure.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.ifinalframework.boot.autoconfigure.web.cors.CorsProperties;
import org.ifinalframework.security.config.HttpSecurityConfigurer;
import org.ifinalframework.security.web.authentication.ResultAuthenticationHandler;
import org.ifinalframework.security.web.authentication.www.BearerAuthenticationFilter;
import org.ifinalframework.security.web.authentication.www.RemoteAuthenticationFilter;
import org.ifinalframework.security.web.authentication.www.RemoteAuthenticationService;
import org.ifinalframework.util.Proxies;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * SecurityAutoConfiguration.
 *
 * <h2>Http Basic</h2>
 *
 *
 * <pre class="code">
 *      http.httpBasic();
 * </pre>
 *
 * @author iimik
 * @version 1.5.0
 * @see org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
 * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter
 * @see org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
 * @since 1.5.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CorsProperties.class, SecurityProperties.class})
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FinalSecurityAutoConfiguration {

    private final HttpSecurityConfigurer httpSecurityConfigurer;

    public FinalSecurityAutoConfiguration(ObjectProvider<List<HttpSecurityConfigurer>> httpSecurityConfigurer) {
        this.httpSecurityConfigurer = Proxies.composite(HttpSecurityConfigurer.class, httpSecurityConfigurer.getIfAvailable());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        final PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (delegatingPasswordEncoder instanceof DelegatingPasswordEncoder) {
            ((DelegatingPasswordEncoder) delegatingPasswordEncoder).setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        }
        return delegatingPasswordEncoder;
    }

    @Bean
    @ConditionalOnBean(RemoteAuthenticationService.class)
    public RemoteAuthenticationFilter remoteAuthenticationFilter(RemoteAuthenticationService remoteAuthenticationService) {
        return new RemoteAuthenticationFilter(new WebAuthenticationDetailsSource(), remoteAuthenticationService);
    }

    @Bean
    public SecurityFilterChain corsSecurityFilterChain(ApplicationContext applicationContext, HttpSecurity http,
                                                       CorsProperties corsProperties,
                                                       SecurityProperties securityProperties) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if (Objects.nonNull(corsProperties.getAllowedHeaders()) && corsProperties.getAllowedHeaders().length > 0) {
            corsConfiguration.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders()));
        }
        if (Objects.nonNull(corsProperties.getAllowedMethods()) && corsProperties.getAllowedMethods().length > 0) {
            corsConfiguration.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods()));
        }
        if (Objects.nonNull(corsProperties.getAllowedOrigins()) && corsProperties.getAllowedOrigins().length > 0) {
            corsConfiguration.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins()));
        }
        if (Objects.nonNull(corsProperties.getAllowedOriginPatterns()) && corsProperties.getAllowedOriginPatterns().length > 0) {
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList(corsProperties.getAllowedOriginPatterns()));
        }


        if (Objects.nonNull(corsProperties.getAllowCredentials())) {
            corsConfiguration.setAllowCredentials(corsProperties.getAllowCredentials());
        }
        if (Objects.nonNull(corsProperties.getMaxAge())) {
            corsConfiguration.setMaxAge(corsProperties.getMaxAge());
        }

        if (StringUtils.hasLength(corsProperties.getMapping())) {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration(corsProperties.getMapping(), corsConfiguration);
            http.cors().configurationSource(source);
        }


        session(http, securityProperties.getSession());


        basic(http, securityProperties.getBasic());
        rememberMe(http, securityProperties.getRememberMe());
        anonymous(http, securityProperties.getAnonymous());

        http.csrf().disable();

        // 登出
        http.logout(configurer -> {
            configurer.logoutUrl(securityProperties.getLogout().getUrl());
        });

        applicationContext.getBeanProvider(BearerAuthenticationFilter.class).ifAvailable(filter -> {
            logger.info("addFilterBefore UsernamePasswordAuthenticationFilter: {}", filter.getClass().getName());
            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        });

        applicationContext.getBeanProvider(RemoteAuthenticationFilter.class).ifAvailable(filter -> {
            logger.info("addFilterBefore UsernamePasswordAuthenticationFilter: {}", filter.getClass().getName());
            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        });

        applicationContext.getBeanProvider(UsernamePasswordAuthenticationFilter.class).ifAvailable(filter -> {
            logger.info("addFilterAt UsernamePasswordAuthenticationFilter: {}", filter.getClass().getName());
            http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        });

        final ResultAuthenticationHandler resultAuthenticationHandler = applicationContext.getBeanProvider(ResultAuthenticationHandler.class).getIfAvailable();
        // 表单登录
        http.formLogin(configurer -> {
            configurer.loginPage("/api/login").permitAll();
            if (Objects.nonNull(resultAuthenticationHandler)) {
                configurer.successHandler(resultAuthenticationHandler);
                configurer.failureHandler(resultAuthenticationHandler);

            }
        });

        // 异常处理
        if (Objects.nonNull(resultAuthenticationHandler)) {
            http.exceptionHandling(configurer -> configurer.accessDeniedHandler(resultAuthenticationHandler));
        }

        httpSecurityConfigurer.authorizeRequests(http.authorizeRequests());

        return http.build();
    }

    private void session(HttpSecurity http, SecurityProperties.SessionProperties sessionProperties) throws Exception {
        final SessionManagementConfigurer<HttpSecurity> sessionManagement = http.sessionManagement();
        if (Objects.nonNull(sessionProperties.getCreationPolicy())) {
            sessionManagement.sessionCreationPolicy(sessionProperties.getCreationPolicy());
        }

    }

    private void basic(HttpSecurity http, SecurityProperties.BasicProperties basicProperties) throws Exception {
        if (Objects.isNull(basicProperties) || !Boolean.TRUE.equals(basicProperties.getEnable())) {
            return;
        }
        http.httpBasic();
    }

    private void rememberMe(HttpSecurity http, SecurityProperties.RememberMeProperties rememberMeProperties) throws Exception {
        if (Objects.isNull(rememberMeProperties) || !Boolean.TRUE.equals(rememberMeProperties.getEnable())) {
            return;
        }
        http.rememberMe().alwaysRemember(Boolean.TRUE.equals(rememberMeProperties.getAlwaysRemember()));

    }

    private void anonymous(HttpSecurity http, SecurityProperties.AnonymousProperties anonymousProperties) throws Exception {
        if (Objects.isNull(anonymousProperties) || !Boolean.TRUE.equals(anonymousProperties.getEnable())) {
            return;
        }
        final AnonymousConfigurer<HttpSecurity> anonymous = http.anonymous();

        if (Objects.nonNull(anonymousProperties.getAuthorities())) {
            anonymous.authorities(anonymousProperties.getAuthorities());
        }


    }


}
