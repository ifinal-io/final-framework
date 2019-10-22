package org.finalframework.data.coding.entity;

import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.lang.NonNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-26 20:05
 * @since 1.0
 */
public interface Property<T, P extends Property<T, P>> {

    Element getElement();

    TypeElement getJavaTypeElement();

    TypeElement getMetaTypeElement();

    String getName();

    String getTable();

    String getColumn();

    @NonNull
    PersistentType getPersistentType();

    List<TypeElement> getViews();

    default boolean isEnum() {
        return false;
    }

    boolean hasView(@NonNull Class<?> view);

    boolean unique();

    boolean nonnull();

    boolean insertable();

    boolean updatable();

    boolean selectable();

    boolean placeholder();

    String getType();

    String getMapKeyType();

    String getMapValueType();

    boolean isIdProperty();

    boolean isVersionProperty();

    PrimaryKeyType getPrimaryKeyType();

    boolean isCollection();

    boolean isMap();

    boolean isArray();

    boolean isReference();

    ReferenceMode referenceMode();

    List<String> referenceProperties();

    String referenceColumn(String property);

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    Entity toEntity();

    boolean hasView(@NonNull TypeElement view);

    boolean isTransient();
}
