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

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 调用支持库
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-27 20:57:40
 * @since 1.0
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
    EvaluationContext createEvaluationContext(@NonNull OperationContext context, @Nullable Object result, @Nullable Throwable e);

    List<String> findExpressions(String expression);


    /**
     * 返回指定的字符串是否为一个表达式
     *
     * @param expression 表达式字符串
     */
    boolean isExpression(@Nullable String expression);

    @NonNull
    String generateExpression(@NonNull String expression);

}
