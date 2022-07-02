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

package org.ifinalframework.security.web.authentication;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.json.Json;
import org.ifinalframework.security.core.TokenUserAuthenticationService;
import org.ifinalframework.security.jwt.JwtTokenUtil;
import org.ifinalframework.security.core.TokenUser;

import lombok.extern.slf4j.Slf4j;

/**
 * ResultAuthenticationSuccessHandler.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
@Slf4j
@Component
public class ResultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenUserAuthenticationService<? extends TokenUser> tokenAuthenticationService;

    public ResultAuthenticationSuccessHandler(TokenUserAuthenticationService<? extends TokenUser> tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;


    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TokenUser tokenUser = tokenAuthenticationService.token(authentication);

        String token = JwtTokenUtil.createToken(Json.toJson(tokenUser));

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        Result<Object> result = R.success(tokenUser);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(Json.toJson(result));
    }

}


