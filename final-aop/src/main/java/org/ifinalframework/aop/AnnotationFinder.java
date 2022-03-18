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

package org.ifinalframework.aop;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 查找声明在注解元素{@link AnnotatedElement}上的{@link Annotation}, 期中注解元素包含{@link Class}、{@link Method}和{@link Parameter}。
 *
 * @author ilikly
 * @version 1.0.0
 * @see AnnotatedElementUtils#findMergedRepeatableAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#findAllMergedAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#getAllMergedAnnotations(AnnotatedElement, Class)
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationFinder<A> {

    A findAnnotations(@NonNull AnnotatedElement ae);

}
