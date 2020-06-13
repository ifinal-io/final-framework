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

package org.finalframework.monitor.annotation;

import org.finalframework.monitor.executor.Tracer;
import org.finalframework.monitor.handler.TraceOperationHandler;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.AdviceAnnotation;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 在方法执行之前，向上下文中注入{@code trace}链路追踪变量。
 *
 * @author likly
 * @version 1.0
 * @date 2019-07-01 13:10:18
 * @since 1.0
 */
@AdviceAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorTrace {

    @AliasFor("trace")
    String value() default "trace";

    @AliasFor("value")
    String trace() default "trace";

    Class<? extends OperationHandler> handler() default TraceOperationHandler.class;

    Class<? extends Executor> executor() default Tracer.class;
}
