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

package org.ifinalframework.monitor.annotation;

import lombok.Getter;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.core.lang.Transient;

/**
 * 监控级别
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
@Getter
public enum MonitorLevel implements IEnum<Integer> {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR");

    private final Integer code;

    private final String desc;

    MonitorLevel(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

}
