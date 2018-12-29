package com.ilikly.finalframework.coding.element;

import com.ilikly.finalframework.data.annotation.PrimaryKey;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:18
 * @since 1.0
 */
public class BaseProperty<T extends Entity, P extends Property<T, P>> implements Property<T, P> {

    private final T entity;
    private final ProcessingEnvironment processEnv;
    private final Elements elements;
    private final Types types;
    private final Element element;
    private final String name;
    private final String rawType;
    private final String type;
    private final String componentType;
    private final String mapKeyType;
    private final String mapValueType;
    private final boolean isCollection;
    private final boolean isMap;
    private final boolean isIdProperty;

    public BaseProperty(T entity, ProcessingEnvironment processEnv, Element element) {
        this.entity = entity;
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.rawType = element.asType().toString();
        this.type = types.erasure(element.asType()).toString();
        this.isCollection = types.isAssignable(types.erasure(element.asType()), elements
                .getTypeElement("java.util.Collection")
                .asType());
        if (isCollection) {
            componentType = ((DeclaredType) element.asType()).getTypeArguments().get(0).toString();
        } else {
            componentType = null;
        }

        this.isMap = types.isAssignable(types.erasure(element.asType()), elements
                .getTypeElement("java.util.Map")
                .asType());
        if (isMap) {
            List<? extends TypeMirror> arguments = ((DeclaredType) element.asType()).getTypeArguments();
            this.mapKeyType = arguments.get(0).toString();
            this.mapValueType = arguments.get(1).toString();
        } else {
            mapKeyType = null;
            mapValueType = null;
        }

        isIdProperty = hasAnnotation(PrimaryKey.class);

    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getComponentType() {
        return componentType;
    }

    @Override
    public String getRawType() {
        return rawType;
    }

    @Override
    public String getMapKeyType() {
        return mapKeyType;
    }

    @Override
    public String getMapValueType() {
        return mapValueType;
    }

    @Override
    public boolean isIdProperty() {
        return isIdProperty;
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }

    @Override
    public boolean isMap() {
        return isMap;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return element.getAnnotation(annotationType);
    }


}
