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

import org.springframework.lang.NonNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class TypeElements {

    private final Types types;

    private final Elements elements;

    private final Map<Class<?>, TypeElement> typeElementMap = new HashMap<>(16);

    private final Map<Class<?>, TypeMirror> elementTypes = new HashMap<>(16);

    public TypeElements(final Types types, final Elements elements) {

        this.types = types;
        this.elements = elements;
        this.init();
    }

    private void init() {

        Stream.of(LocalDateTime.class, LocalDate.class, LocalTime.class, Object.class)
                .forEach(this::initTypeElements);
        elementTypes
                .put(Collection.class, types.getDeclaredType(elements.getTypeElement(Collection.class.getCanonicalName()),
                        types.getWildcardType(null, null)));
        elementTypes.put(List.class, types.getDeclaredType(elements.getTypeElement(List.class.getCanonicalName()),
                types.getWildcardType(null, null)));
        elementTypes.put(Set.class, types
                .getDeclaredType(elements.getTypeElement(Set.class.getCanonicalName()), types.getWildcardType(null, null)));
        elementTypes.put(Map.class,
                types.getDeclaredType(elements.getTypeElement(Map.class.getCanonicalName()),
                        types.getWildcardType(null, null), types.getWildcardType(null, null)));
    }

    private void initTypeElements(final Class<?> type) {

        typeElementMap.put(type, elements.getTypeElement(type.getCanonicalName()));
    }

    public boolean isCollection(final @NonNull Element element) {

        return isAssignable(element.asType(), elementTypes.get(Collection.class)) || isSubtype(element.asType(),
                elementTypes.get(Collection.class));
    }

    public boolean isAssignable(final @NonNull Element element, final @NonNull Element target) {

        return isAssignable(types.erasure(element.asType()), types.erasure(target.asType()));
    }

    public boolean isAssignable(final @NonNull TypeMirror type, final @NonNull TypeMirror target) {

        return types.isAssignable(type, target);
    }

    public boolean isSubtype(final @NonNull TypeMirror element, final @NonNull TypeMirror target) {

        return types.isSubtype(element, target);

    }

    public boolean isSubtype(final @NonNull Element element, final @NonNull Class<?> target) {

        return isSubtype(element, getTypeElement(target));
    }

    public boolean isSubtype(final @NonNull Element element, final @NonNull Element target) {

        return types.isSubtype(types.erasure(element.asType()), types.erasure(target.asType()));

    }

    public boolean isList(final @NonNull Element element) {

        return isAssignable(element.asType(), elementTypes.get(List.class)) || isSubtype(element.asType(),
                elementTypes.get(List.class));
    }

    public boolean isSet(final @NonNull Element element) {

        return isAssignable(element.asType(), elementTypes.get(Set.class)) || isSubtype(element.asType(),
                elementTypes.get(Set.class));
    }

    public boolean isMap(final @NonNull Element element) {

        return isAssignable(element.asType(), elementTypes.get(Map.class)) || isSubtype(element.asType(),
                elementTypes.get(Map.class));
    }

    public boolean isObject(final Element element) {

        return isSameType(element, Object.class);
    }

    public boolean isSameType(final @NonNull Element element, final @NonNull Class<?> target) {

        return isSameType(element, getTypeElement(target));
    }

    public boolean isSameType(final @NonNull Element element, final @NonNull Element target) {

        return isSameType(element.asType(), target.asType());
    }

    public boolean isSameType(final @NonNull TypeMirror type, final @NonNull TypeMirror target) {

        return types.isSameType(type, target);
    }

    public TypeElement getTypeElement(final Class<?> type) {

        return typeElementMap.containsKey(type) ? typeElementMap.get(type)
                : elements.getTypeElement(type.getCanonicalName());
    }

}

