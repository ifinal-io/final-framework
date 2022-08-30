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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import org.ifinalframework.security.core.TokenAuthenticationService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * CookieAuthenticationConverter.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 */
@Slf4j
public class CookieAuthenticationConverter implements AuthenticationConverter {

    @Setter
    private String cookieName = "token";
    @Setter
    private String cookiePath = "/";
    private final TokenAuthenticationService tokenAuthenticationService;

    public CookieAuthenticationConverter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {

        Cookie cookie = getCookie(request);

        if (Objects.isNull(cookie)) {
            return null;
        }

        String token = cookie.getValue();

        return tokenAuthenticationService.authenticate(token);
    }

    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies) || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName()) && cookiePath.equals(cookie.getPath())) {
                return cookie;
            }
        }

        return null;
    }
}


