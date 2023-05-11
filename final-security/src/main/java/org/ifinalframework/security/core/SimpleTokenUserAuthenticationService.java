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

package org.ifinalframework.security.core;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.IUser;

import lombok.Setter;

/**
 * JwtTokenAuthenticationService.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
@Configuration
@ConditionalOnMissingBean(TokenAuthenticationService.class)
public class SimpleTokenUserAuthenticationService extends AbsTokenUserAuthenticationService<SimpleTokenUser> implements ApplicationContextAware, InitializingBean {

    @Setter
    private ApplicationContext applicationContext;
    private UserDetailsService userDetailsService;

    public SimpleTokenUserAuthenticationService() {
        super(SimpleTokenUser.class);
    }

    @Override
    public SimpleTokenUser user(Authentication authentication) {
        SimpleTokenUser user = new SimpleTokenUser();
        Object principal = authentication.getPrincipal();
        if(principal instanceof IUser){
            IUser<?> iUser = (IUser<?>) principal;
            user.setName(iUser.getName());
            user.setId((Long) iUser.getId());
        }
        user.setUsername(authentication.getName());
        user.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return user;
    }

    @Override
    public Authentication authenticate(SimpleTokenUser token) {
        List<String> roles = Optional.ofNullable(token.getRoles()).orElse(Collections.emptyList());
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        if (Objects.nonNull(userDetailsService)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUsername());
            if (userDetails instanceof IUser) {
                UserContextHolder.setUser((IUser<?>) userDetails);
            } else {
                UserContextHolder.setUser(token);
            }
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.eraseCredentials();
            return authenticationToken;
        }
        UserContextHolder.setUser(token);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token, null, authorities);
        authenticationToken.eraseCredentials();
        return authenticationToken;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeanProvider(UserDetailsService.class).ifAvailable(it -> this.userDetailsService = it);
    }
}


