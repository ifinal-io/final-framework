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

import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.user.UserSupplier;
import org.ifinalframework.core.IUser;

/**
 * SecurityUserSupplier.
 *
 * @author ilikly
 * @version 1.4.1
 * @since 1.4.1
 */
@Component
public class SecurityUserSupplier implements UserSupplier, Ordered {
    @Override
    public IUser<?> get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (IUser<?>) principal;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}


