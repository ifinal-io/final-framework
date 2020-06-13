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


import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-21 22:44:11
 * @since 1.0
 */
public class MethodEvaluationContext extends MethodBasedEvaluationContext {

    private final Set<String> unavailableVariables = new HashSet<>(1);

    public MethodEvaluationContext(MethodExpressionRootObject rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }

}
