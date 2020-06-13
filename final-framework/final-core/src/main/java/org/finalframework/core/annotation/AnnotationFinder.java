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

package org.finalframework.core.annotation;

import lombok.NonNull;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-28 14:03:28
 * @see AnnotatedElementUtils
 * @since 1.0
 */
public interface AnnotationFinder {
    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     */
    default <A extends Annotation> Collection<A> findAllAnnotations(@NonNull AnnotatedElement ae, @NonNull Class<A> annType) {
        return null;
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},不包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     */
    <A extends Annotation> Collection<A> findLocalAnnotations(@NonNull AnnotatedElement ae, @NonNull Class<A> annType);
}
