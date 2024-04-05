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

package org.ifinalframework.beans;

import org.springframework.core.convert.TypeDescriptor;

/**
 * 增强{@link org.springframework.beans.AbstractNestablePropertyAccessor#newValue(Class, TypeDescriptor, String)}方法，以支持自家义实例化过程。
 * BeanTypeDescriptorFactory.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 * @see org.springframework.beans.AbstractNestablePropertyAccessor
 */
public interface BeanTypeDescriptorFactory<T> {

    /**
     * 返回是否支持目标类的实例化。
     * @param type 目标类
     * @param typeDescriptor 目标类描述
     * @return
     */
    boolean support(Class<?> type, TypeDescriptor typeDescriptor);

    /**
     * 实例化目标类
     * @param type 目标类
     * @param typeDescriptor 目标类描述
     * @return
     */
    T create(Class<?> type, TypeDescriptor typeDescriptor);

}
