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

package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.finalframework.spring.aop.OperationHandlerSupport;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:13:02
 * @since 1.0
 */
public class AbsOperationHandlerSupport implements OperationHandlerSupport {

    /**
     * 表达式的开头标记
     */
    private static final String EXPRESSION_PREFIX = "{";
    /**
     * 表达式的结尾标记
     */
    private static final String EXPRESSION_SUFFIX = "}";


    private static final Pattern EXPRESSION_PATTEN = Pattern.compile("\\{[^\\{\\}]*\\}");


    private final OperationExpressionEvaluator evaluator;

    public AbsOperationHandlerSupport(OperationExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public EvaluationContext createEvaluationContext(OperationContext context, Object result, Throwable e) {
        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
                context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result, e);

    }

    public static void main(String[] args) {
        String str = "select * from order where createdUser = ${#1currentUser1} and  depart = ${'currentOrg' + #id} and status = 'VALID'";
        Matcher matcher = EXPRESSION_PATTEN.matcher(str);// 指定要匹配的字符串

        List<String> matchStrs = new ArrayList<>();

        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }

        for (int i = 0; i < matchStrs.size(); i++) {
            System.out.println(matchStrs.get(i));
        }

    }

    @Override
    public List<String> findExpressions(String expression) {
        Matcher matcher = EXPRESSION_PATTEN.matcher(expression);// 指定要匹配的字符串
        List<String> matchStrs = new ArrayList<>();
        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }
        return matchStrs;
    }

    /**
     * 以{@link AbsOperationHandlerSupport#EXPRESSION_PREFIX}开头，并且以{@link AbsOperationHandlerSupport#EXPRESSION_SUFFIX}结尾的字符串，为一个表达式。
     *
     * @param expression 表达式字符串
     */
    @Override
    public boolean isExpression(@Nullable String expression) {
        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression.endsWith(EXPRESSION_SUFFIX);
    }

    @Override
    public String generateExpression(@NonNull String expression) {
        Asserts.isEmpty(expression, "expression is empty");
        return expression.trim().substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }
}
