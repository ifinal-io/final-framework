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

package org.ifinalframework.security.user;

import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.context.user.UserSupplier;
import org.ifinalframework.core.IUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * SecurityContextUserSupplier.
 *
 * @author ilikly
 * @version 1.4.1
 * @see SecurityContextHolder
 * @since 1.4.1
 */
@Slf4j
@Component
public class SecurityContextUserSupplier implements UserSupplier, Ordered {
    @Override
    public IUser<?> get() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return null;
        }


        Object principal = authentication.getPrincipal();

        if (Objects.isNull(principal)) {
            return null;
        }


        if (principal instanceof IUser) {
            return (IUser<?>) principal;
        }

        if (logger.isDebugEnabled()) {
            logger.info("principal is not instanceof IUser: {}", principal);
        }

        return null;

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}


