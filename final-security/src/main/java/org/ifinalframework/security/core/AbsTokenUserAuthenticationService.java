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

import org.springframework.security.core.Authentication;

import org.ifinalframework.json.Json;
import org.ifinalframework.security.jwt.JwtTokenUtil;

/**
 * AbsTokenUserAuthenticationService.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 */
public abstract class AbsTokenUserAuthenticationService<T extends TokenUser> implements TokenUserAuthenticationService<T> {

    private final Class<T> tokenUserClass;

    public AbsTokenUserAuthenticationService(Class<T> tokenUserClass) {
        this.tokenUserClass = tokenUserClass;
    }

    @Override
    public String token(Authentication authentication) {
        return JwtTokenUtil.createToken(Json.toJson(user(authentication)));
    }

    @Override
    public Authentication authenticate(String token) {
        String payload = JwtTokenUtil.getPayload(token);
        T tokenUser = Json.toObject(payload, tokenUserClass);
        return authenticate(tokenUser);
    }

}


