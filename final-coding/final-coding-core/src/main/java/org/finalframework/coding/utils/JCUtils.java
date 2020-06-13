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

package org.finalframework.coding.utils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Java 编译工具集
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-11 16:09:31
 * @since 1.0
 */
public interface JCUtils {


    /**
     * 如果指的{@link TypeElement}是{@link ElementKind#ENUM}，则返回 {@code true}。
     *
     * @param element 指定的 {@link TypeElement}
     * @return {@code true} if the test {@link TypeElement}'s kind is {@link ElementKind#ENUM}
     */
    static boolean isEnum(TypeElement element) {
        return element.getKind() == ElementKind.ENUM;
    }


}
