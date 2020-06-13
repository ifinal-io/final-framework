/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.*;
import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 09:43:13
 * @since 1.0
 */
public class OperationInvocationSupport {
    private final Map<OperationCacheKey<Operation>, OperationMetadata<Operation>> metadataCache = new ConcurrentHashMap<>(1024);
    private final OperationConfiguration configuration;

    public OperationInvocationSupport(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    protected List<OperationContext<Operation>> getOperationContexts(Object target, Method method, Object[] args) {
        final Class<?> targetClass = getTargetClass(target);
        final Collection<? extends Operation> operations = configuration.getOperationSource().getOperations(method, targetClass);
        if (Assert.nonEmpty(operations)) {
            return operations.stream()
                    .map(operation -> getOperationContext(operation, method, args, target, targetClass))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private OperationContext<Operation> getOperationContext(Operation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        OperationMetadata<Operation> metadata = getOperationMetadata(operation, method, targetClass);
        return new BaseOperationContext<>(metadata, target, args);
    }

    private OperationMetadata<Operation> getOperationMetadata(Operation operation, Method method, Class<?> targetClass) {
        final OperationCacheKey<Operation> cacheKey = new OperationCacheKey<>(operation, method, targetClass);
        OperationMetadata<Operation> metadata = this.metadataCache.get(cacheKey);
        if (metadata == null) {
            metadata = new OperationMetadata<>(operation, method, targetClass);
            this.metadataCache.put(cacheKey, metadata);
        }
        return metadata;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

}
