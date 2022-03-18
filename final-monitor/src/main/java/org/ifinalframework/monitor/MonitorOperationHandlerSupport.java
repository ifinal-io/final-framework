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

package org.ifinalframework.monitor;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.aop.OperationHandlerSupport;
import org.ifinalframework.context.expression.MethodMetadata;
import org.ifinalframework.monitor.annotation.MonitorLevel;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MonitorOperationHandlerSupport extends OperationHandlerSupport {

    @Nullable
    String generateName(@NonNull String[] name, @NonNull String delimiter, @NonNull MethodMetadata metadata,
        @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateOperator(@NonNull String operator, @NonNull MethodMetadata metadata,
        @NonNull EvaluationContext evaluationContext);

    Object generateTarget(@NonNull String target, @NonNull MethodMetadata metadata,
        @NonNull EvaluationContext evaluationContext);

    Object generateAttribute(@NonNull String attribute, @NonNull MethodMetadata metadata,
        @NonNull EvaluationContext evaluationContext);

    MonitorLevel level(AnnotationAttributes annotation);

}
