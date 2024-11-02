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

package org.ifinalframework.data.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.ITenant;

import java.util.Objects;

/**
 * SecurityContextTenantSupplier.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Component
@ConditionalOnClass(SecurityContextHolder.class)
public class SecurityContextTenantSupplier implements TenantSupplier<Object>, Ordered {
    @Override
    public Object get() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }

        final Object principal = authentication.getPrincipal();

        if (principal instanceof ITenant) {
            final Long tenant = ((ITenant) principal).getTenant();
            if (Objects.isNull(tenant) || tenant < 0) {
                return null;
            }
            return tenant;
        }

        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1000;
    }
}
