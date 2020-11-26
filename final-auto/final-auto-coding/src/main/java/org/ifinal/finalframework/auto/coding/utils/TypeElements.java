package org.ifinal.finalframework.auto.coding.utils;


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
 * @version 1.0.0
 * @since 1.0.0
 */
public final class TypeElements {
    private final Types types;
    private final Elements elements;
    private final Map<Class<?>, TypeElement> typeElements = new HashMap<>(16);
    private final Map<Class<?>, TypeMirror> elementTypes = new HashMap<>(16);


    public TypeElements(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;
        this.init();
    }

    private void init() {
//        Collection.class, List.class, Set.class, Map.class,

        Stream.of(LocalDateTime.class, LocalDate.class, LocalTime.class, Object.class)
                .forEach(this::initTypeElements);
        elementTypes.put(Collection.class, types.getDeclaredType(elements.getTypeElement(Collection.class.getCanonicalName()), types.getWildcardType(null, null)));
        elementTypes.put(List.class, types.getDeclaredType(elements.getTypeElement(List.class.getCanonicalName()), types.getWildcardType(null, null)));
        elementTypes.put(Set.class, types.getDeclaredType(elements.getTypeElement(Set.class.getCanonicalName()), types.getWildcardType(null, null)));
        elementTypes.put(Map.class, types.getDeclaredType(elements.getTypeElement(Map.class.getCanonicalName()), types.getWildcardType(null, null), types.getWildcardType(null, null)));
        ;
    }

    private void initTypeElements(Class<?> type) {
        typeElements.put(type, elements.getTypeElement(type.getCanonicalName()));
    }

    public boolean isCollection(@NonNull Element element) {
        return isAssignable(element.asType(), elementTypes.get(Collection.class)) || isSubtype(element.asType(), elementTypes.get(Collection.class));
    }

    public boolean isList(@NonNull Element element) {
        return isAssignable(element.asType(), elementTypes.get(List.class)) || isSubtype(element.asType(), elementTypes.get(List.class));
    }

    public boolean isSet(@NonNull Element element) {
        return isAssignable(element.asType(), elementTypes.get(Set.class)) || isSubtype(element.asType(), elementTypes.get(Set.class));
    }

    public boolean isMap(@NonNull Element element) {
        return isAssignable(element.asType(), elementTypes.get(Map.class)) || isSubtype(element.asType(), elementTypes.get(Map.class));
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

    public boolean isSubtype(@NonNull TypeMirror element, @NonNull TypeMirror target) {
        return types.isSubtype(element, target);

    }


    public boolean isObject(Element element) {
        return isSameType(element, Object.class);
    }

    public boolean isAssignable(@NonNull Element element, @NonNull Element target) {
        return isAssignable(types.erasure(element.asType()), types.erasure(target.asType()));
    }

    public boolean isAssignable(@NonNull TypeMirror type, @NonNull TypeMirror target) {
        return types.isAssignable(type, target);
    }

    public TypeElement getTypeElement(Class<?> type) {
        return typeElements.containsKey(type) ? typeElements.get(type) : elements.getTypeElement(type.getCanonicalName());
    }

}

