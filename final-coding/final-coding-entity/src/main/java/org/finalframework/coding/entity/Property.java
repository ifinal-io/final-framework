package org.finalframework.coding.entity;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.finalframework.data.annotation.Default;
import org.finalframework.data.annotation.Final;
import org.finalframework.data.annotation.PrimaryKey;
import org.finalframework.data.annotation.ReadOnly;
import org.finalframework.data.annotation.Sharding;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.annotation.Version;
import org.finalframework.data.annotation.Virtual;
import org.finalframework.data.annotation.WriteOnly;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-26 20:05
 * @since 1.0
 */
public interface Property {

    @NonNull
    Element getElement();

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

    boolean unique();

    boolean nonnull();

    /**
     * @return true if don't have {@link Transient} annotation and have {@link Default} annotaion.
     * @see Default
     */
    boolean isDefault();

    /**
     * @see Final
     */
    boolean isFinal();

    /**
     * @see Virtual
     */
    boolean isVirtual();

    /**
     * @see Sharding
     */
    boolean isShardable();

    /**
     * @see ReadOnly
     */
    boolean isWritable();


    boolean updatable();

    /**
     * @see WriteOnly
     */
    boolean isReadable();

    boolean placeholder();

    TypeMirror getType();

    String getMapKeyType();

    String getMapValueType();

    /**
     * @see PrimaryKey
     */
    boolean isIdProperty();

    /**
     * @see Version
     */
    boolean isVersion();

    PrimaryKeyType getPrimaryKeyType();

    /**
     * @see javax.lang.model.type.PrimitiveType
     * @see TypeKind#isPrimitive()
     */
    boolean isPrimitive();

    /**
     * @see javax.lang.model.element.ElementKind#ENUM
     */
    boolean isEnum();

    boolean isCollection();

    /**
     * @see List
     */
    boolean isList();

    /**
     * @see Set
     */
    boolean isSet();

    /**
     * @see java.util.Map
     */
    boolean isMap();

    boolean isArray();

    /**
     * @see org.finalframework.data.annotation.Reference
     */
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

    /**
     * @see Transient
     */
    boolean isTransient();

}
