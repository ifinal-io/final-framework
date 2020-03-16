package org.finalframework.coding.entity;

import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.lang.NonNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-26 20:05
 * @since 1.0
 */
public interface Property {

    /**
     * @return
     */
    Element getElement();

    VariableElement getField();

    ExecutableElement getWriteMethod();

    ExecutableElement getReadMethod();

    TypeElement getJavaTypeElement();

    TypeElement getMetaTypeElement();

    String getName();

    String getColumn();

    @NonNull
    PersistentType getPersistentType();

    List<TypeElement> getViews();

    boolean hasView(@NonNull Class<?> view);

    boolean unique();

    boolean nonnull();

    boolean isDefault();

    boolean isWriteOnly();

    boolean updatable();

    boolean isReadOnly();

    boolean placeholder();

    TypeMirror getType();

    String getMapKeyType();

    String getMapValueType();

    boolean isIdProperty();

    boolean isVersion();

    PrimaryKeyType getPrimaryKeyType();

    boolean isPrimitive();

    boolean isEnum();

    boolean isCollection();

    boolean isList();

    boolean isSet();

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
