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

package org.finalframework.spring.aop.annotation;

import org.finalframework.data.annotation.Transient;

/**
 * AOP切入点，用于描述缓存 {@link java.lang.annotation.Annotation} 的调用顺序。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-10 22:14:18
 * @since 1.0
 */
@Transient
public enum CutPoint {
    /**
     * 在方法执行之前
     */
    BEFORE(1),
    /**
     * 在方法返回之后
     */
    AFTER_RETURNING(2),
    /**
     * 在方法抛出异常后
     */
    AFTER_THROWING(4),
    /**
     * 在方法执行之后，包含 {@link #AFTER_RETURNING} 和 {@link #AFTER_THROWING}。
     */
    AFTER(6);

    private final Integer value;

    CutPoint(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
