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
 */

package org.finalframework.web.auth;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.finalframework.core.IUser;

/**
 * Just only do parse token from the {@link HttpServletRequest} to {@link IUser} but don't check the user info.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface TokenService<T extends IUser<?>> {

    /**
     * return the {@link IUser} which parsed from the {@link HttpServletRequest}.
     *
     * @param request  http request
     * @param response http response
     * @return the {@link IUser}
     */
    @Nullable
    T token(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response);

}
