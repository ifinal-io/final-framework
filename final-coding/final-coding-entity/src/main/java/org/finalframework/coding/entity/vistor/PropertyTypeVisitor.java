package org.finalframework.coding.entity.vistor;


import org.finalframework.coding.entity.Property;
import org.springframework.lang.NonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:44:54
 * @since 1.0
 */
public class PropertyTypeVisitor extends SimpleTypeVisitor8<TypeElement, Property.Builder> {

    @NonNull
    private final ProcessingEnvironment pv;
    @NonNull
    private final Elements elements;
    @NonNull
    private final Types types;

    public PropertyTypeVisitor(@NonNull ProcessingEnvironment pv) {
        this.pv = pv;
        this.elements = pv.getElementUtils();
        this.types = pv.getTypeUtils();
    }

    /**
     * Represents a primitive type.  These include
     * {@code boolean}, {@code byte}, {@code short}, {@code int},
     * {@code long}, {@code char}, {@code float}, and {@code double}.
     *
     * @return
     */
    @Override
    public TypeElement visitPrimitive(PrimitiveType type, Property.Builder builder) {
        builder.primitive(true)
                .array(false)
                .collection(false)
                .list(false)
                .set(false)
                .map(false);
        return getPrimitiveTypeElement(type.getKind());
    }

    @Override
    public TypeElement visitArray(ArrayType array, Property.Builder builder) {
        builder.primitive(false).array(true)
                .collection(false).list(false).set(false).map(false);
        TypeMirror type = array.getComponentType();

        TypeKind kind = type.getKind();
        if (kind.isPrimitive()) {
            return getPrimitiveTypeElement(kind);
        }

        if (TypeKind.DECLARED == kind && !isCollection(type)) {
            DeclaredType declaredType = (DeclaredType) type;
            Element element = declaredType.asElement();
            if (element instanceof TypeElement) {
                return (TypeElement) element;
            }
        }

        throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
    }

    @Override
    public TypeElement visitDeclared(DeclaredType type, Property.Builder builder) {

        boolean isCollection = isCollection(type);
        boolean isList = isList(type);
        boolean isSet = isSet(type);
        boolean isMap = isMap(type);

        builder.primitive(false).array(false)
                .collection(isCollection).list(isList).set(isSet).map(isMap);

        if (isList || isSet) {
            TypeMirror elementType = type.getTypeArguments().get(0);
            TypeKind kind = elementType.getKind();
            if (kind.isPrimitive()) return getPrimitiveTypeElement(kind);
            if (TypeKind.DECLARED == kind && !isCollection) {
                DeclaredType declaredType = (DeclaredType) elementType;
                Element element = declaredType.asElement();
                if (element instanceof TypeElement) {
                    return (TypeElement) element;
                }
            }

        } else if (isMap) {
            List<? extends TypeMirror> typeArguments = type.getTypeArguments();
            TypeMirror keyType = typeArguments.get(0);
            TypeMirror valueType = typeArguments.get(1);
            // TODO 1. 校验KEY、VALUE必须是基础类型
            return getTypeElement(Map.class);
        }

        if (!isCollection) {
            Element element = type.asElement();
            if (element instanceof TypeElement) {
                return (TypeElement) element;
            }
        }


        throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
    }

    private TypeElement getPrimitiveTypeElement(TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return getTypeElement(Boolean.class);
            case BYTE:
                return getTypeElement(Byte.class);
            case SHORT:
                return getTypeElement(Short.class);
            case INT:
                return getTypeElement(Integer.class);
            case LONG:
                return getTypeElement(Long.class);
            case CHAR:
                return getTypeElement(Character.class);
            case FLOAT:
                return getTypeElement(Float.class);
            case DOUBLE:
                return getTypeElement(Double.class);
        }

        throw new IllegalArgumentException("不支持的类型：" + kind);
    }

    private boolean isCollection(TypeMirror type) {
        return types.isAssignable(types.erasure(type), types.erasure(getTypeElement(Collection.class).asType()));
    }

    private boolean isList(TypeMirror type) {
        return types.isAssignable(types.erasure(type), types.erasure(getTypeElement(List.class).asType()));
    }

    private boolean isSet(TypeMirror type) {
        return types.isAssignable(types.erasure(type), types.erasure(getTypeElement(Set.class).asType()));
    }

    private boolean isMap(TypeMirror type) {
        return types.isAssignable(types.erasure(type), types.erasure(getTypeElement(Map.class).asType()));
    }


    private TypeElement getTypeElement(Class<?> type) {
        return elements.getTypeElement(type.getCanonicalName());
    }


}

