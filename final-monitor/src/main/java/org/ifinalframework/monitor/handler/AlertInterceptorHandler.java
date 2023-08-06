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

package org.ifinalframework.monitor.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.InterceptorHandler;
import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.core.IException;
import org.ifinalframework.monitor.MonitorException;
import org.ifinalframework.monitor.context.AlertContext;
import org.ifinalframework.monitor.executor.Alerter;

import org.slf4j.MDC;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AlertInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements
        InterceptorHandler<Alerter, AnnotationAttributes> {

    @Override
    public void handle(final @NonNull Alerter executor, final @NonNull InvocationContext context,
                       final @NonNull AnnotationAttributes annotation,
                       final Object result, final Throwable throwable) {

        final AlertContext.Builder builder = AlertContext.builder();
        builder.name(annotation.getString("name"))
                .level(level(annotation))
                .trace(MDC.get("trace"))
                .timestamp(System.currentTimeMillis());

        if (throwable != null) {
            builder.exception(throwable instanceof IException
                    ? new MonitorException(500, throwable.getMessage(), (IException) throwable)
                    : new MonitorException(500, throwable.getMessage()));
        }

        executor.alert(builder.build());
    }

}
