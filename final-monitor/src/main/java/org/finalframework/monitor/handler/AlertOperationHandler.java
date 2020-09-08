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

package org.finalframework.monitor.handler;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.core.Assert;
import org.finalframework.data.IException;
import org.finalframework.monitor.MonitorException;
import org.finalframework.monitor.context.AlertContext;
import org.finalframework.monitor.executor.Alerter;
import org.finalframework.monitor.operation.AlertOperation;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.MDC;
import org.springframework.expression.EvaluationContext;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 18:16
 * @since 1.0
 */
@SpringComponent
public class AlertOperationHandler<T> extends AbsMonitorOperationHandlerSupport implements OperationHandler<Alerter, AlertOperation> {

    @Override
    public Object before(Alerter executor, OperationContext<AlertOperation> context) {
        if (CutPoint.BEFORE == context.operation().point()) {
            alert(executor, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Alerter executor, OperationContext<AlertOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            alert(executor, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Alerter executor, OperationContext<AlertOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            alert(executor, context, null, throwable);
        }
    }

    @Override
    public void after(Alerter executor, OperationContext<AlertOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            alert(executor, context, result, throwable);
        }
    }

    private void alert(Alerter executor, OperationContext<AlertOperation> context, Object result, Throwable throwable) {
        final AlertOperation operation = context.operation();
        final OperationMetadata<AlertOperation> metadata = context.metadata();
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

        final AlertContext.Builder<T> builder = AlertContext.builder();
        builder.name(operation.name())
                .level(operation.level())
                .operator(generateOperator(operation.operator(), metadata, evaluationContext))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());


        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new MonitorException((IException) throwable)
                    : new MonitorException(500, throwable.getMessage()));
        }


        final Map<String, String> attributes = operation.attributes();
        if (Assert.nonEmpty(attributes)) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                builder.addAttribute(entry.getKey(), generateAttribute(entry.getValue(), metadata, evaluationContext));
            }
        }

        executor.alert(builder.build());
    }
}
