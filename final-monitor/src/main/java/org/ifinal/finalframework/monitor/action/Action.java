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
 *
 */

package org.ifinal.finalframework.monitor.action;

import org.ifinal.finalframework.monitor.annotation.ActionMonitor;
import org.ifinal.finalframework.monitor.annotation.MonitorLevel;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Action 上下文，描述一个 Action 的动作
 *
 * @author likly
 * @version 1.0.0
 * @see ActionMonitor
 * @since 1.0.0
 */
@Getter
@Setter
public class Action {

    /**
     * 名称
     *
     * @see ActionMonitor#name()
     * @see ActionMonitor#value()
     */
    private String name;

    /**
     * 类型
     *
     * @see ActionMonitor#type()
     */
    private String type;

    /**
     * 动作
     *
     * @see ActionMonitor#code()
     */
    private String code;

    /**
     * 级别
     *
     * @see ActionMonitor#level()
     */
    private MonitorLevel level;

    /**
     * 目标
     *
     * @see ActionMonitor#target()
     */
    private Object target;

    /**
     * 属性
     */
    private Map<String, Object> attributes;

    /**
     * 异常
     */
    private Throwable exception;

    /**
     * 追踪
     */
    private String trace;

    /**
     * 时间戳
     */
    private Long timestamp;

}
