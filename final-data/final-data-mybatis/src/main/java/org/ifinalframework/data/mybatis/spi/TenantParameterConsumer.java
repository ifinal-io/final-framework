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

package org.ifinalframework.data.mybatis.spi;

import org.springframework.stereotype.Component;

import org.ifinalframework.data.core.TenantSupplier;
import org.ifinalframework.data.util.TenantUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * TenantMapParameterConsumer.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Component
public class TenantParameterConsumer implements MapParameterConsumer {

    private final List<TenantSupplier<?>> tenantSuppliers;

    public TenantParameterConsumer(List<TenantSupplier<?>> tenantSuppliers) {
        this.tenantSuppliers = tenantSuppliers;
    }

    @Override
    public void accept(Map<String, Object> parameter, Class<?> mapper, Method method) {

        final Object tenant = tenantSuppliers.stream().map(Supplier::get).filter(Objects::nonNull).findFirst().orElse(null);
        final Class<?> entityClass = (Class<?>) parameter.get(EntityClassParameterConsumer.ENTITY_CLASS_PARAM_NAME);
        if (TenantUtils.isTenant(entityClass)) {
            parameter.put("tenant", tenant);
        } else {
            parameter.put("tenant", null);
        }

    }

}
