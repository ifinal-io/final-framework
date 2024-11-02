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
 */

package org.ifinalframework.data.auto.utils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Java 编译工具集
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JCUtils {

    /**
     * 如果指的{@link TypeElement}是{@link ElementKind#ENUM}，则返回 {@code true}。
     *
     * @param element 指定的 {@link TypeElement}
     * @return {@code true} if the test {@link TypeElement}'s kind is {@link ElementKind#ENUM}
     */
    static boolean isEnum(final TypeElement element) {

        return element.getKind() == ElementKind.ENUM;
    }

}
