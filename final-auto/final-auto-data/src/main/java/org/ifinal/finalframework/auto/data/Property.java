package org.ifinal.finalframework.auto.data;

import org.ifinal.finalframework.annotation.data.PersistentType;
import org.ifinal.finalframework.annotation.data.ReferenceMode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Property {

    @NonNull
    Element getElement();

    @Nullable
    TypeElement getTypeHandler();

    @Nullable
    VariableElement getField();

    @Nullable
    ExecutableElement getWriteMethod();

    @Nullable
    ExecutableElement getReadMethod();

    /**
     * return the {@literal javaType} of {@link Property}. if this property is a primary type or it's package type,
     * return the primary package type. if this property is {@link String},return {@link String}. if this property is a
     * {@link List} or {@link Set}, return the element of that. if this property is a {@link java.util.Map} type, return
     * {@link java.util.Map}. if this property is a {@literal Entity}, return the type of {@literal entity}.
     *
     * @return javaType
     */
    @NonNull
    TypeElement getJavaTypeElement();

    TypeElement getMetaTypeElement();

    @NonNull
    String getName();

    @NonNull
    String getColumn();

    @NonNull
    PersistentType getPersistentType();

    @Nullable
    List<TypeElement> getViews();

    boolean hasView(@NonNull Class<?> view);

    boolean isDefault();

    boolean isFinal();

    boolean isVirtual();

    boolean isSharding();

    boolean isReadOnly();


    boolean isWriteOnly();

    default boolean isWriteable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isDefault();
    }

    default boolean isModifiable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isFinal();
    }


    boolean placeholder();

    TypeMirror getType();


    boolean isKeyword();

    boolean isIdProperty();

    boolean isVersion();

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

    boolean isAnnotationPresent(Class<? extends Annotation> annotationType);

    Entity toEntity();

    boolean hasView(@NonNull TypeElement view);

    boolean isTransient();

}
