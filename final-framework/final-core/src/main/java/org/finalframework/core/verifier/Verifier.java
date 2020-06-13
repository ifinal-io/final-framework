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

package org.finalframework.core.verifier;

/**
 * 业务数据校验器
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-26 14:05:18
 * @since 1.0
 */
@FunctionalInterface
public interface Verifier<T> {
    /**
     * 校验业务数据是否合法，合法返回 {@code true}，否则返回 {@code false}。
     *
     * @param data 业务数据
     * @return 返回业务数据是否合法。
     */
    boolean verify(T data);
}
