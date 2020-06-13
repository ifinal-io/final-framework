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

package org.finalframework.monitor.builder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.finalframework.monitor.annotation.MonitorTrace;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 17:38
 * @since 1.0
 */
@SpringComponent
@SuppressWarnings("unused")
public class TraceOperationAnnotationBuilder implements OperationAnnotationBuilder<MonitorTrace, TraceOperation> {

    @Override
    public TraceOperation build(Class<?> type, MonitorTrace ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public TraceOperation build(Method method, MonitorTrace ann) {
        return build((AnnotatedElement) method, ann);
    }

    private TraceOperation build(AnnotatedElement ae, MonitorTrace ann) {
        return TraceOperation.builder()
                .name(ae.toString())
                .trace(ann.value())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();
    }
}
