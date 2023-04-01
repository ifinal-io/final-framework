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

package org.ifinalframework.validation;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * ValidationGroupsProvider.
 *
 * @author ilikly
 * @version 1.5.0
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see org.springframework.validation.beanvalidation.MethodValidationInterceptor
 * @since 1.5.0
 */
public interface MethodValidationGroupsProvider {

    /**
     * @param method 方法
     * @param target 目标对象
     * @param args   参数列表
     *
     * @return 分组校验
     */
    @Nullable
    List<Class<?>> getValidationGroups(@NonNull Method method, @NonNull Object target, @NonNull Object[] args);
}
