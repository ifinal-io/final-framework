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

package org.finalframework.spring.aop;

import org.finalframework.core.Assert;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:00:25
 * @since 1.0
 */
public interface OperationSource {

    default boolean matches(Method method, @Nullable Class<?> targetClass) {
        return Assert.nonEmpty(getOperations(method, targetClass));
    }

    @Nullable
    Collection<? extends Operation> getOperations(Method method, @Nullable Class<?> targetClass);

    @NonNull
    default Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

}
