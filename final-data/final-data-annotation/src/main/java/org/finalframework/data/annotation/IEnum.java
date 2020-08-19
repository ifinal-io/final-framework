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

package org.finalframework.data.annotation;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 通用枚举接口，为实现在程序中使用枚举常量，而存储时使用枚举码{@link IEnum#getCode()}。
 * <p/>
 * `final-json`会将实现该接口的{@link Enum}转化为对应的{@link IEnum#getCode()}的值，并且在Json序列化JavaBean对象时，对{@link IEnum}
 * 类型的属性，新加一个`xxxName`的属性，值为{@link IEnum#getDesc()}。
 * <p/>
 * 详情查看：<a href="https://final.ilikly.com/json/#stronger-enum">JSON枚举增强</a>
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:07
 * @since 1.0
 */
public interface IEnum<T> {

    /**
     * 返回表示该枚举常量的code
     */
    @JsonValue
    T getCode();

    /**
     * 返回对该枚举常量的描述信息
     *
     * @see #getDesc()
     * @deprecated 已过期
     */
    @Deprecated
    default String getDescription() {
        return getDesc();
    }

    /**
     * 返回对该枚举常量的描述信息
     */
    String getDesc();
}
