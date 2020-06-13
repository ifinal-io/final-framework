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

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Operation 注解解析器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:20:28
 * @see OperationAnnotationBuilder
 * @since 1.0
 */
public interface OperationAnnotationParser {

    /**
     * 解析标记在{@link Class}上的 {@link java.lang.annotation.Annotation},并将其构建成对应的{@link Operation}
     */
    @Nullable
    Collection<? extends Operation> parseOperationAnnotation(Class<?> type);

    /**
     * 解析标记在 {@link Method} 上的 {@link java.lang.annotation.Annotation},并将其构建成对应的{@link Operation}
     */
    @Nullable
    Collection<? extends Operation> parseOperationAnnotation(Method method);
}
