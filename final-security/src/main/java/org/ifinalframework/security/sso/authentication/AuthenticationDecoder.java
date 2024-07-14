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

import org.springframework.security.core.Authentication;

/**
 * AuthenticationDecoder
 *
 * @author iimik
 * @see AuthenticationEncoder
 * @since 1.5.6
 **/
@FunctionalInterface
public interface AuthenticationDecoder<S extends IAuthentication<?>> {
    /**
     * 从认证源解密{@link Authentication}
     *
     * @param source 认证源
     * @return
     */
    Authentication decode(S source);
}

