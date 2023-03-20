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

package org.ifinalframework.web.servlet.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.IUser;
import org.ifinalframework.web.auth.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * TokenFilterTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class TokenFilterTest {

    @Mock
    private TokenService<User> tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TokenFilter tokenFilter;

    @Test
    void doFilterInternal() throws Exception {

        User user = new User(-1L, "Test");
        when(tokenService.token(any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(user);

        tokenFilter.doFilterInternal(request, response, filterChain);

        assertEquals(user, UserContextHolder.getUser());

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User implements IUser<Long> {

        private Long id;

        private String name;

    }

}
