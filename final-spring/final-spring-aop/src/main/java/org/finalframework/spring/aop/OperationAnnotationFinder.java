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

package org.finalframework.spring.aop;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * 查找声明在注解元素{@link AnnotatedElement}上的{@link Annotation},
 * 期中注解元素包含{@link Class}、{@link Method}和{@link Parameter}。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:43:08
 * @see AnnotatedElementUtils#findMergedRepeatableAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#findAllMergedAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#getAllMergedAnnotations(AnnotatedElement, Class)
 * @since 1.0
 */
public interface OperationAnnotationFinder<A extends Annotation> {

    /**
     * 查询声明在{@link Class}上的注解元素{@link A}
     *
     * @param type 类
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Class<?> type);

    /**
     * 查询声明在{@link Method}上的注解元素{@link A}
     *
     * @param method 方法
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Method method);

    /**
     * 查询声明在{@link Parameter}上的注解元素{@link A}
     *
     * @param parameter 方法参数
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Parameter parameter);

}
