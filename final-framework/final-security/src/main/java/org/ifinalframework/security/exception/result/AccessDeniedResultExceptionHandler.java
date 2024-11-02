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

package org.ifinalframework.security.exception.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.exception.result.ResultExceptionHandler;
import org.ifinalframework.core.ResponseStatus;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;

/**
 * AccessDeniedResultExceptionHandler.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
@Component
@ConditionalOnClass(AccessDeniedException.class)
public class AccessDeniedResultExceptionHandler implements ResultExceptionHandler<AccessDeniedException> {
    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public boolean supports(@NonNull Throwable throwable) {
        return throwable instanceof AccessDeniedException;
    }

    @NonNull
    @Override
    public Result<?> handle(@NonNull AccessDeniedException throwable) {

        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return R.failure(401, "未登录");
        }

        return R.failure(ResponseStatus.FORBIDDEN.getCode(), throwable.getMessage());
    }
}


