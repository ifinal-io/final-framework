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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.finalframework.monitor.executor.Recorder;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.finalframework.spring.aop.annotation.OperationAttribute;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 22:56:04
 * @see org.finalframework.monitor.action.Action
 * @see org.finalframework.monitor.action.ActionListener
 * @since 1.0
 */

@Documented
@Repeatable(MonitorAction.List.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorAction {

    /**
     * 操作名称
     *
     * @see #value()
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 操作名称
     *
     * @see #name()
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 操作类型
     */
    int type() default 0;

    /**
     * 操作动作
     */
    int action() default 0;

    /**
     * 操作者
     */
    @AliasFor("who")
    String operator() default "";

    @AliasFor("operator")
    String who() default "";

    /**
     * 操作目标
     */
    String target() default "";

    /**
     * 级别
     */
    MonitorLevel level() default MonitorLevel.INFO;

    CutPoint point() default CutPoint.AFTER_RETURNING;

    OperationAttribute[] attributes() default {};

    Class<? extends OperationHandler> handler() default OperationHandler.class;

    Class<? extends Executor> executor() default Recorder.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        MonitorAction[] value();
    }
}
