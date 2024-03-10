/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.security.sso.api.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ifinalframework.core.IUser;
import org.ifinalframework.security.sso.authentication.Authentication;
import org.ifinalframework.security.sso.authentication.AuthenticationEncoder;
import org.ifinalframework.security.sso.authentication.AuthenticationService;

import jakarta.annotation.Resource;

/**
 * AuthenticationController
 *
 * @author iimik
 * @since 1.5.6
 **/
@RestController
@RequestMapping("/api/sso/authentication")
public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private AuthenticationEncoder authenticationEncoder;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String authentication(@RequestHeader(value = "X-SERVICE", required = false) String service, IUser<?> user) {
        final Authentication authentication = authenticationService.lode(service, user);
        return authenticationEncoder.encode(authentication);
    }
}
