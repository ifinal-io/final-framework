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
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * JwtTokenAuthenticationService.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
@Component
public class SimpleTokenUserAuthenticationService extends AbsTokenUserAuthenticationService<SimpleTokenUser> {
    public SimpleTokenUserAuthenticationService() {
        super(SimpleTokenUser.class);
    }

    @Override
    public SimpleTokenUser user(Authentication authentication) {
        SimpleTokenUser user = new SimpleTokenUser();
        user.setUsername(authentication.getName());
        user.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return user;
    }

    @Override
    public Authentication authenticate(SimpleTokenUser token) {
        List<String> roles = Optional.ofNullable(token.getRoles()).orElse(Collections.emptyList());
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(token, null, authorities);
    }
}


