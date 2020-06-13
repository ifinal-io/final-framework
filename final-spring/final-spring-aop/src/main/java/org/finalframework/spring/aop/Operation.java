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

import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

/**
 * AOP 切点操作描述
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:40:18
 * @since 1.0
 */
public interface Operation {

    /**
     * 切点名称
     */
    @NonNull
    String name();

    default int order() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 调用者
     */
    @NonNull
    Class<? extends OperationHandler> handler();

    @NonNull
    Class<? extends Executor> executor();

}
