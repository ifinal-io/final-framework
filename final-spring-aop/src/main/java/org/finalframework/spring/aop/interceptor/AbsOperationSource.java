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


import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationSource;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:14:15
 * @since 1.0
 */
public abstract class AbsOperationSource implements OperationSource {
    private final Collection<? extends Operation> NULL_CACHE_OPERATION = Collections.emptyList();
    private final Map<Object, Collection<? extends Operation>> operationCache = new ConcurrentHashMap<>(1024);

    @Override
    public Collection<? extends Operation> getOperations(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        Object cacheKey = getCacheKey(method, targetClass);
        Collection<? extends Operation> cached = this.operationCache.get(cacheKey);

        if (cached != null) {
            return (cached != NULL_CACHE_OPERATION ? cached : null);
        }

        Collection<? extends Operation> operations = computeOperations(method, targetClass);
        if (operations != null) {
            final ArrayList<? extends Operation> list = new ArrayList<>(operations);
            list.sort(Comparator.comparingInt(Operation::order));
            this.operationCache.put(cacheKey, Collections.unmodifiableList(list));
        } else {
            this.operationCache.put(cacheKey, NULL_CACHE_OPERATION);
        }
        return operations;
    }

    @Nullable
    private Collection<? extends Operation> computeOperations(Method method, @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        // 注解可能是声明在接口上的，因此不需要找到实现类的方法
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
//        @SuppressWarnings("all")
//       final Method specificMethod = method;

        // First try is the method in the target class.
        Collection<? extends Operation> opDef = findOperations(specificMethod);

        if (opDef != null && !opDef.isEmpty()) {
            return opDef;
        }

        // Second try is the caching action on the target class.
        opDef = findOperations(specificMethod.getDeclaringClass());
        if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
            return opDef;
        }

        if (specificMethod != method) {
            // Fallback is to look at the original method.
            opDef = findOperations(method);
            if (opDef != null && !opDef.isEmpty()) {
                return opDef;
            }
            // Last fallback is the class of the original method.
            opDef = findOperations(method.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                return opDef;
            }
        }

        return null;
    }

    @Nullable
    protected abstract Collection<? extends Operation> findOperations(Method method);

    @Nullable
    protected abstract Collection<? extends Operation> findOperations(Class<?> clazz);

    protected boolean allowPublicMethodsOnly() {
        return false;
    }
}
