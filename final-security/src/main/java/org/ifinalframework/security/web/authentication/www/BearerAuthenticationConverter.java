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

package org.ifinalframework.security.web.authentication.www;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import org.ifinalframework.security.core.TokenAuthenticationService;

/**
 * BearerAuthenticationConverter.
 *
 * @author ilikly
 * @version 1.4.0
 * @see org.springframework.security.web.authentication.www.BasicAuthenticationConverter
 * @since 1.4.0
 */
public class BearerAuthenticationConverter implements AuthenticationConverter {
    private static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";


    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private final TokenAuthenticationService tokenAuthenticationService;

    public BearerAuthenticationConverter(TokenAuthenticationService tokenAuthenticationService) {
        this(new WebAuthenticationDetailsSource(), tokenAuthenticationService);
    }

    public BearerAuthenticationConverter(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource, TokenAuthenticationService tokenAuthenticationService) {
        this.authenticationDetailsSource = authenticationDetailsSource;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BEARER)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BEARER)) {
            throw new BadCredentialsException("Empty bearer authentication token");
        }
        final String token = header.substring(AUTHENTICATION_SCHEME_BEARER.length() + 1);
        Authentication authentication = tokenAuthenticationService.authenticate(token);
        if (authentication instanceof AbstractAuthenticationToken) {
            ((AbstractAuthenticationToken) authentication).setDetails(this.authenticationDetailsSource.buildDetails(request));
        }
        return authentication;
    }


}


