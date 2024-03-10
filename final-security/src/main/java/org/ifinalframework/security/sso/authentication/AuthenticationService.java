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

package org.ifinalframework.security.sso.authentication;


import org.ifinalframework.core.IUser;

/**
 * AuthenticationService
 *
 * @author iimik
 * @since 1.5.6
 **/
@FunctionalInterface
public interface AuthenticationService<U extends IUser> {
    /**
     * 加载用户认证信息
     * @param service
     * @param user
     * @return
     */
    Authentication lode(String service, U user);
}
