package org.ifinal.finalframework.auto.data;

import org.ifinal.finalframework.annotation.data.ReferenceMode;
import org.springframework.lang.NonNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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

    @NonNull
    String getName();

    @NonNull
    String getColumn();


    boolean isDefault();

    boolean isFinal();

    boolean isVirtual();

    boolean isReadOnly();

    boolean isWriteOnly();

    default boolean isWriteable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isDefault();
    }

    default boolean isModifiable() {
        return !isTransient() && !isVirtual() && !isReadOnly() && !isFinal();
    }


    TypeMirror getType();


    boolean isKeyword();

    boolean isIdProperty();

    boolean isVersion();

    boolean isCollection();

    boolean isMap();


    boolean isReference();

    ReferenceMode referenceMode();

    List<String> referenceProperties();

    String referenceColumn(String property);

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    boolean isAnnotationPresent(Class<? extends Annotation> annotationType);

    boolean isTransient();

}
