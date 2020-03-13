package org.finalframework.coding.utils;


import org.springframework.lang.NonNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 21:14:58
 * @since 1.0
 */
public final class TypeElements {
    private final Types types;
    private final Elements elements;
    private final Map<Class<?>, TypeElement> typeElements = new HashMap<>(16);


    public TypeElements(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;
        this.init();
    }

    private void init() {
        Stream.of(Collection.class, List.class, Set.class, Map.class,
                LocalDateTime.class, LocalDate.class, LocalTime.class, Object.class)
                .forEach(this::initTypeElements);
    }

    private void initTypeElements(Class<?> type) {
        typeElements.put(type, elements.getTypeElement(type.getCanonicalName()));
    }

    public boolean isCollection(@NonNull Element element) {
        return isSubtype(element, Collection.class);
    }

    public boolean isList(@NonNull Element element) {
        return isSubtype(element, typeElements.get(List.class));
    }

    public boolean isSet(@NonNull Element element) {
        return isSubtype(element, typeElements.get(Set.class));
    }

    public boolean isMap(@NonNull Element element) {
        return isSubtype(element, typeElements.get(Map.class));
    }

    public boolean isSameType(@NonNull Element element, @NonNull Class<?> target) {
        return isSameType(element, getTypeElement(target));
    }

    public boolean isSameType(@NonNull Element element, @NonNull Element target) {
        return isSameType(element.asType(), target.asType());
    }

    public boolean isSameType(@NonNull TypeMirror type, @NonNull TypeMirror target) {
        return types.isSameType(type, target);
    }


    public boolean isSubtype(@NonNull Element element, @NonNull Class<?> target) {
        return isSubtype(element, getTypeElement(target));
    }

    public boolean isSubtype(@NonNull Element element, @NonNull Element target) {
        return types.isSubtype(types.erasure(element.asType()), types.erasure(target.asType()));
    }

    public boolean isObject(Element element) {
        return isSameType(element, Object.class);
    }

    public boolean isAssignable(@NonNull TypeMirror type, @NonNull TypeMirror target) {
        return types.isAssignable(type, target);
    }

    public TypeElement getTypeElement(Class<?> type) {
        return typeElements.containsKey(type) ? typeElements.get(type) : elements.getTypeElement(type.getCanonicalName());
    }

}

