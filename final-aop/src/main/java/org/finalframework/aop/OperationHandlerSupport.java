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
 */

package org.finalframework.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 调用支持库
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationHandlerSupport {

    /**
     * 创建表达式计算域
     *
     * @param context 操作上下文
     * @param result  方法的返回结果，可能为{@code null}
     * @param e       方法抛出的异常，可能为{@code null}
     */
    @NonNull
    EvaluationContext createEvaluationContext(@NonNull InvocationContext context, @Nullable Object result,
        @Nullable Throwable e);

}
