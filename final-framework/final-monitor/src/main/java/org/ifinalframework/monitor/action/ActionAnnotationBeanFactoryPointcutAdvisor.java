/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.monitor.action;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.AnnotationAttributesAnnotationBuilder;
import org.ifinalframework.aop.single.SingleAnnotationBeanFactoryPointcutAdvisor;
import org.ifinalframework.monitor.annotation.OperationAction;
import org.ifinalframework.monitor.handler.ActionInterceptorHandler;

import jakarta.annotation.Resource;

import java.util.Arrays;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings("serial")
public class ActionAnnotationBeanFactoryPointcutAdvisor extends
        SingleAnnotationBeanFactoryPointcutAdvisor<OperationAction, AnnotationAttributes, OperationActionHandler> {

    @Resource
    private OperationActionHandler operationActionHandler;

    public ActionAnnotationBeanFactoryPointcutAdvisor() {
        super(OperationAction.class, new AnnotationAttributesAnnotationBuilder<>(),
                Arrays.asList(new ActionInterceptorHandler()));
    }

    @Override
    @NonNull
    protected OperationActionHandler getExecutor(final AnnotationAttributes annotation) {

        return operationActionHandler;
    }

}
