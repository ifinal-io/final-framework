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

import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:01:12
 * @since 1.0
 */
public class OperationCacheKey<O extends Operation> implements Comparable<OperationCacheKey> {

    private final O operation;

    private final AnnotatedElementKey methodCacheKey;

    public OperationCacheKey(O operation, Method method, Class<?> targetClass) {
        this.operation = operation;
        this.methodCacheKey = new AnnotatedElementKey(method, targetClass);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OperationCacheKey)) {
            return false;
        }
        OperationCacheKey otherKey = (OperationCacheKey) other;
        return (this.operation.equals(otherKey.operation) &&
                this.methodCacheKey.equals(otherKey.methodCacheKey));
    }

    @Override
    public int hashCode() {
        return (this.operation.hashCode() * 31 + this.methodCacheKey.hashCode());
    }

    @Override
    public String toString() {
        return this.operation + " on " + this.methodCacheKey;
    }

    @Override
    public int compareTo(OperationCacheKey other) {
        int result = this.operation.getClass().getSimpleName().compareTo(other.operation.getClass().getSimpleName());
        if (result == 0) {
            result = this.methodCacheKey.compareTo(other.methodCacheKey);
        }
        return result;
    }
}
