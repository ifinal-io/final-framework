/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinal.finalframework.web.auth;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.core.IUser;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface AuthService<T extends IUser<?>, A extends Annotation> {

    /**
     * @param user     the {@link IUser} login, maybe null.
     * @param auth     the {@link Annotation} link auth.
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param handler  the handler
     */
    default void auth(@Nullable T user, @NonNull A auth, @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @NonNull Object handler) {
        auth(user, auth, handler);
    }

    /**
     * @param user    user
     * @param auth    auth
     * @param handler handler
     */
    void auth(@Nullable T user, @NonNull A auth, @NonNull Object handler);

}
