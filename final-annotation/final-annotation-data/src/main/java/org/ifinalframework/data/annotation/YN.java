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

package org.ifinalframework.data.annotation;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否有效枚举，用于标记记录是否有效。
 *
 * @author iimik
 * @version 1.0.0
 * @see IEntity
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
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
     * 已删除
     *
     * @since 1.4.2
     */
    DELETED(-1, "已删除");
    /**
     * 枚举码
     */
    private final Integer code;

    private final String desc;

}
