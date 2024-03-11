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

package org.ifinalframework.security.web.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.result.ResultFunctionConsumerComposite;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.json.Json;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一认证接口返回.
 *
 * @author iimik
 * @version 1.3.3
 * @since 1.3.3
 */
@Slf4j
@Component
public class ResultAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, AccessDeniedHandler {

    @Resource
    private ResultFunctionConsumerComposite resultFunctionConsumerComposite;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Result<?> result = R.success();
        doResult(response, result);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Result<?> result = R.failure(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        doResult(response, result);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        final Result<?> result = R.failure(403, "您没有权限访问：" + request.getMethod() + " " + request.getRequestURI());
        doResult(response,result);
    }


    private void doResult(HttpServletResponse response, Result<?> result) throws IOException {
        resultFunctionConsumerComposite.apply(result);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Json.toJson(result));
    }
}


