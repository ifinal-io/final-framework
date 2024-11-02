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

package org.ifinalframework.data.auto.entity;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Property {

    Element getElement();

    /**
     * return the {@literal javaType} of {@link Property}. if this property is a primary type or it's package type,
     * return the primary package type. if this property is {@link String},return {@link String}. if this property is a
     * {@link List} or {@link Set}, return the element of that. if this property is a {@link java.util.Map} type, return
     * {@link java.util.Map}. if this property is a {@literal Entity}, return the type of {@literal entity}.
     *
     * @return javaType
     */
    TypeElement getJavaTypeElement();

    String getName();

    TypeMirror getType();

    boolean isIdProperty();

    boolean isVersion();

    boolean isCollection();

    boolean isMap();

    boolean isReference();

    List<String> referenceProperties();

    String referenceColumn(String property);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    boolean isAnnotationPresent(Class<? extends Annotation> annotationType);

    boolean isTransient();

}
