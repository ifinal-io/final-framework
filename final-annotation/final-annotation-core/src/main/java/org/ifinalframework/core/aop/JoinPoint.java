/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.core.aop;

import org.ifinalframework.core.lang.Transient;

/**
 * AOP切入点，用于描述缓存 {@link java.lang.annotation.Annotation} 的调用顺序。
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public enum JoinPoint {
    /**
     * 在方法执行之前
     */
    BEFORE,
    /**
     * 在方法返回之后
     */
    AFTER_RETURNING,
    /**
     * 在方法抛出异常后
     */
    AFTER_THROWING,
    /**
     * 在方法执行之后，包含 {@link #AFTER_RETURNING} 和 {@link #AFTER_THROWING}。
     */
    AFTER

}
