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

package org.ifinalframework.security.authentication.event.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.security.core.TokenUser;
import org.ifinalframework.security.core.TokenUserAuthenticationService;

/**
 * UserContextAuthenticationSuccessEventListener.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
@Component
public class UserContextAuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Resource
    private TokenUserAuthenticationService<? extends TokenUser> tokenUserAuthenticationService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        final TokenUser user = tokenUserAuthenticationService.user(event.getAuthentication());
        UserContextHolder.setUser(user);
    }
}
