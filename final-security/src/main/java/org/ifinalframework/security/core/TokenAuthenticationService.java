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

package org.ifinalframework.security.core;

import org.springframework.security.core.Authentication;

/**
 * TokenAuthenticationService.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
public interface TokenAuthenticationService {
    /**
     * generate a {@code token} form of the {@link  Authentication}
     *
     * @param authentication authentication
     * @return a token
     */
    String token(Authentication authentication);

    /**
     * parse the {@link Authentication} from of the {@code token}.
     *
     * @param token token
     * @return an {@link Authentication}
     */
    Authentication authenticate(String token);
}
