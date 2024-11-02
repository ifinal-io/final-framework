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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.ifinalframework.data.annotation.AbsTenantUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SecurityContextTenantSupplierTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class SecurityContextTenantSupplierTest {

    private final SecurityContextTenantSupplier supplier = new SecurityContextTenantSupplier();

    @Test
    void get() {
        Assertions.assertNull(supplier.get());

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
        Assertions.assertNull(supplier.get());

        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "user"));
        Assertions.assertNull(supplier.get());

        final AbsTenantUser tenantUser = new AbsTenantUser();
        tenantUser.setTenant(1L);
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(tenantUser, ""));
        Assertions.assertEquals(1L, supplier.get());

        tenantUser.setTenant(-1L);
        Assertions.assertNull(supplier.get());

    }

    @Test
    void order() {
        assertEquals(Integer.MIN_VALUE + 1000, supplier.getOrder());
    }
}