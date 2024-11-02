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

package org.ifinalframework.data.domain;

import org.springframework.stereotype.Component;

import org.ifinalframework.context.exception.NotFoundException;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IUser;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DefaultDomainResourceServiceRegistry.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Component
public class DefaultDomainServiceRegistry implements DomainServiceRegistry {
    private final Map<String, DomainService<Long, IEntity<Long>, IUser<?>>> domainServiceMap = new LinkedHashMap<>();

    @Override
    public <K extends Serializable, T extends IEntity<K>, U extends IUser<?>> DomainService<K, T, U> getDomainService(String resource) {
        final DomainService<Long, IEntity<Long>, IUser<?>> domainService = domainServiceMap.get(resource);
        if (Objects.isNull(domainService)) {
            throw new NotFoundException("not found domainService for resource of " + resource);
        }
        return (DomainService<K, T, U>) domainService;
    }

    @Override
    public void registry(String resource, DomainService domainService) {
        this.domainServiceMap.put(resource, domainService);
    }

}
