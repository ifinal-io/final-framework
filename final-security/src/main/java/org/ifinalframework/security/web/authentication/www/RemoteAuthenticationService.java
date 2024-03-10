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

package org.ifinalframework.security.web.authentication.www;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 远程认证获取服务，从请求中获取凭证，然后通过远程调用认证服务获取认证信息。
 * <p>
 * 建议支持以下凭证的认证：
 * <ol>
 *     <li>{@link org.springframework.http.HttpHeaders#AUTHORIZATION}</li>
 *     <li>{@link org.springframework.http.HttpHeaders#COOKIE}</li>
 * </ol>
 *
 * @author iimik
 * @since 1.5.6
 **/
@FunctionalInterface
public interface RemoteAuthenticationService {

    Authentication load(HttpServletRequest request);
}
