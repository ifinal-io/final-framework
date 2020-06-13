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

package org.finalframework.spring.expression;


import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 09:51:57
 * @since 1.0
 */
public class MethodExpressionEvaluator extends CachedExpressionEvaluator {
    /**
     * Indicate that there is no value variable.
     */
    public static final Object NO_RESULT = new Object();

    /**
     * Indicate that the value variable cannot be used at all.
     */
    public static final Object RESULT_UNAVAILABLE = new Object();

    /**
     * The name of the variable holding the value object.
     */
    private static final String RESULT_VARIABLE = "result";
    private static final String THROWABLE_VARIABLE = "e";

    public EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod, Object result, Throwable e) {
        MethodExpressionRootObject rootObject = new MethodExpressionRootObject(method, args, target, targetClass);
        MethodEvaluationContext evaluationContext = new MethodEvaluationContext(rootObject, targetMethod, args, getParameterNameDiscoverer());
        if (result == RESULT_UNAVAILABLE) {
            evaluationContext.addUnavailableVariable(RESULT_VARIABLE);
        } else if (result != NO_RESULT) {
            evaluationContext.setVariable(RESULT_VARIABLE, result);
        }

        if (e != null) {
            evaluationContext.setVariable(THROWABLE_VARIABLE, e);
        }

        return evaluationContext;
    }


}
