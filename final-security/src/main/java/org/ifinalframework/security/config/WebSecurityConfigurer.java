/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.security.config;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import org.ifinalframework.security.web.authentication.ResultAuthenticationFailureHandler;
import org.ifinalframework.security.web.authentication.ResultAuthenticationSuccessHandler;
import org.ifinalframework.security.web.authentication.TokenUserAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSecurityConfigurer.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
@Slf4j
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final ApplicationContext applicationContext = getApplicationContext();

        FormLoginConfigurer<HttpSecurity> formLoginConfigurer = http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()

                .anyRequest()

                .permitAll()

                .and()

                .formLogin()
                .loginPage("/api/login")
                .permitAll();

        applicationContext.getBeanProvider(ResultAuthenticationSuccessHandler.class).ifAvailable(formLoginConfigurer::successHandler);
        applicationContext.getBeanProvider(ResultAuthenticationFailureHandler.class).ifAvailable(formLoginConfigurer::failureHandler);


        http.httpBasic()

                .and()
                .csrf()

                .disable();

        http.cors(Customizer.withDefaults());


        applicationContext.getBeanProvider(TokenUserAuthenticationFilter.class)
                .ifAvailable(filter -> {
                    logger.info("addFilterBefore UsernamePasswordAuthenticationFilter: {}", filter.getClass().getName());
                    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                });

        applicationContext.getBeanProvider(UsernamePasswordAuthenticationFilter.class)
                .ifAvailable(filter -> {
                    logger.info("addFilterAt UsernamePasswordAuthenticationFilter: {}", filter.getClass().getName());
                    http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
                });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        getApplicationContext().getBeanProvider(UserDetailsService.class)
                        .ifAvailable(userDetailsService -> {
                            try {
                                DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> daoAuthenticationConfigurer = auth.userDetailsService(userDetailsService);
                                daoAuthenticationConfigurer.passwordEncoder(NoOpPasswordEncoder.getInstance());
                                daoAuthenticationConfigurer.addObjectPostProcessor(new ObjectPostProcessor<DaoAuthenticationProvider>() {
                                    @Override
                                    public <O extends DaoAuthenticationProvider> O postProcess(O object) {
                                        object.setForcePrincipalAsString(true);
                                        return object;
                                    }
                                });
                                daoAuthenticationConfigurer.configure(auth);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        });



    }
}


