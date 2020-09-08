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

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.core.Asserts;
import org.finalframework.monitor.annotation.MonitorAlert;
import org.finalframework.monitor.operation.AlertOperation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;
import org.finalframework.spring.aop.annotation.OperationAttribute;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 16:56
 * @since 1.0
 */
@SpringComponent
@SuppressWarnings("unused")
public class AlertOperationAnnotationBuilder implements OperationAnnotationBuilder<MonitorAlert, AlertOperation> {

    public AlertOperation build(Method method, MonitorAlert ann) {
        final String name = Asserts.isBlank(ann.name()) ? method.getDeclaringClass().getSimpleName() + "#" + method.getName() : ann.name();
        final AlertOperation.Builder builder = AlertOperation.builder()
                .name(name)
                .key(ann.key())
                .message(ann.message())
                .operator(ann.operator())
                .target(ann.target())
                .condition(ann.condition())
                .level(ann.level())
                .point(ann.point())
                .handler(ann.handler())
                .executor(ann.executor());

        final OperationAttribute[] attributes = ann.attributes();

        if (Asserts.nonEmpty(attributes)) {
            for (OperationAttribute attribute : attributes) {
                builder.addAttribute(attribute.name(), attribute.value());
            }
        }

        return builder.build();
    }
}
