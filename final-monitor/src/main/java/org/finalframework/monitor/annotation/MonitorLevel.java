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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.annotation.IEnum;
import org.finalframework.annotation.data.Transient;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 监控级别
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-29 17:15:11
 * @since 1.0
 */
@Transient
public enum MonitorLevel implements IEnum<Integer> {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    ;

    private static final Map<Integer, MonitorLevel> cache = Arrays.stream(MonitorLevel.values()).collect(Collectors.toMap(MonitorLevel::getCode, Function.identity()));

    private final Integer code;
    private final String description;

    MonitorLevel(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static MonitorLevel valueOf(Integer code) {
        return cache.get(code);
    }


    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    @SuppressWarnings("unused")
    public String getDesc() {
        return description;
    }
}
