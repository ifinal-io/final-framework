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

package org.ifinalframework.util;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

/**
 * @author ilikly
 * @version 1.0.0
 * @see AnnotatedElementUtils
 * @since 1.0.0
 */
public final class AnnotatedElements {

    private AnnotatedElements() {
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     * @return Annotations
     */
    public static <A extends Annotation> Collection<A> findAllAnnotations(final @NonNull AnnotatedElement ae,
        final @NonNull Class<A> annType) {

        return AnnotatedElementUtils.findAllMergedAnnotations(ae, annType);
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},不包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     * @return Annotations
     */
    public static <A extends Annotation> Collection<A> getAllLocalAnnotations(final @NonNull AnnotatedElement ae,
        final @NonNull Class<A> annType) {

        return AnnotatedElementUtils.getAllMergedAnnotations(ae, annType);
    }

    public boolean isAnnotated(final @NonNull AnnotatedElement ae, final @NonNull String annName) {

        return AnnotatedElementUtils.isAnnotated(ae, annName);
    }

    public boolean isAnnotated(final @NonNull AnnotatedElement ae, final @NonNull Class<? extends Annotation> annType) {

        return AnnotatedElementUtils.isAnnotated(ae, annType);
    }

}
