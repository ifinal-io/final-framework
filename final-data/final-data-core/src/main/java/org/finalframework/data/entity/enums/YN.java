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

package org.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.IEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 是否有效枚举，用于标记记录是否有效。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @see IEntity
 * @since 1.0
 */
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1, "有效"),
    /**
     * 无效
     */
    NO(0, "无效"),
    /**
     * 删除
     * Note: Don't use more, it would be remove in future.
     */
    @Deprecated
    DELETED(-1, "删除");
    /**
     * 枚举码
     */
    private final Integer code;
    private static final Map<Integer, YN> cache = Arrays.stream(values()).collect(Collectors.toMap(YN::getCode, Function.identity()));

    private final String desc;

    YN(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @JsonCreator
    public static YN valueOf(Integer value) {
        return cache.get(value);
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
