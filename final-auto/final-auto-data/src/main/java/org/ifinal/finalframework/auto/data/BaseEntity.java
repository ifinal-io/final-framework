package org.ifinal.finalframework.auto.data;

import org.ifinal.finalframework.data.annotation.Table;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseEntity implements MutableEntity {

    private final TypeElement typeElement;
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final String table;
    private final String type;
    private final List<Property> properties = new LinkedList<>();
    private final Map<String, Property> propertyCache = new LinkedHashMap<>();
    private Property idProperty;

    public BaseEntity(ProcessingEnvironment processEnv, TypeElement typeElement) {
        Elements elements = processEnv.getElementUtils();
        Types types = processEnv.getTypeUtils();
        this.typeElement = typeElement;
        this.packageName = elements.getPackageOf(typeElement).toString();
        this.name = typeElement.getQualifiedName().toString();
        this.table = initTable();
        this.simpleName = typeElement.getSimpleName().toString();
        this.type = types.erasure(typeElement.asType()).toString();

    }

    private String initTable() {
        Table annotation = getAnnotation(Table.class);
        return Asserts.isNull(annotation) ? this.typeElement.getSimpleName().toString() : annotation.value();
    }

    @Override
    public void addProperty(Property property) {

        if (property.isIdProperty()) {
            if (idProperty != null) {
                throw new IllegalArgumentException("the entity must only have only one id property!,entity=" + getType());
            }
            this.idProperty = property;
        }


        if (propertyCache.containsKey(property.getName())) {
            throw new IllegalArgumentException(String.format("the entity have not only one property named %s", property.getName()));
        } else {
            propertyCache.put(property.getName(), property);
            properties.add(property);
        }


    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public TypeElement getElement() {
        return typeElement;
    }

    @Override
    public String getRawType() {
        return typeElement.toString();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public Property getProperty(String name) {
        return propertyCache.get(name);
    }

    @Override
    public Property getIdProperty() {
        return idProperty;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return typeElement.getAnnotation(annotationType);
    }

    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }

    @Override
    @NonNull
    public Iterator<Property> iterator() {
        return properties.iterator();
    }
}
