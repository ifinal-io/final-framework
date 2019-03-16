package org.finalframework.data.coding.entity;

import org.finalframework.data.annotation.MultiColumn;
import org.finalframework.data.annotation.PrimaryKey;
import org.finalframework.data.annotation.ReferenceColumn;
import org.finalframework.data.annotation.enums.ReferenceMode;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:18
 * @since 1.0
 */
public class BaseProperty<T extends Entity, P extends Property<T, P>> implements Property<T, P> {

    private static final Set<String> GETTER_PREFIX = new HashSet<>(Arrays.asList("is", "get"));


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
    private final boolean isReference;
    private final ReferenceMode referenceMode;
    private List<String> referenceProperties;

    public BaseProperty(T entity, ProcessingEnvironment processEnv, Element element) {
        this.entity = entity;
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.element = element;
        this.name = getElementName(element);
        this.rawType = element.asType().toString();
        this.type = types.erasure(element.asType()).toString();

        if (element.getKind().isField()) {
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
        } else {
            this.componentType = null;
            this.mapKeyType = null;
            this.mapValueType = null;
            this.isCollection = false;
            this.isMap = false;
        }

        isIdProperty = hasAnnotation(PrimaryKey.class);

        if (hasAnnotation(ReferenceColumn.class)) {
            final ReferenceColumn referenceColumn = getAnnotation(ReferenceColumn.class);
            isReference = true;
            referenceMode = referenceColumn.mode();
            initReferenceProperties(referenceColumn.properties(), referenceColumn.delimiter());
        } else if (hasAnnotation(MultiColumn.class)) {
            final MultiColumn multiColumn = getAnnotation(MultiColumn.class);
            isReference = true;
            referenceMode = multiColumn.mode();
            initReferenceProperties(multiColumn.properties(), multiColumn.delimiter());
        } else {
            isReference = false;
            referenceMode = null;
            referenceProperties = null;
        }

    }

    private void initReferenceProperties(String[] properties, String delimiter) {
        this.referenceProperties = Arrays.stream(properties)
                .map(property -> {
                    if (property.contains(delimiter)) {
                        return property.split(delimiter)[0];
                    } else {
                        return property;
                    }
                }).collect(Collectors.toList());
    }

    private String getElementName(Element element) {
        if (element.getKind().isField()) {
            return element.getSimpleName().toString();
        }

        final String elementName = element.getSimpleName().toString();

        for (String prefix : GETTER_PREFIX) {
            if (elementName.startsWith(prefix)) {
                String name = elementName.substring(prefix.length());
                return name.substring(0, 1).toLowerCase() + name.substring(1);
            }
        }
        return elementName;
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
        return isCollection ? componentType : type;
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
    public boolean isReference() {
        return isReference;
    }

    @Override
    public ReferenceMode referenceMode() {
        return referenceMode;
    }

    @Override
    public List<String> referenceProperties() {
        return referenceProperties;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return element.getAnnotation(annotationType);
    }


}
