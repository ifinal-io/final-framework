/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.auto.entity;

import org.springframework.lang.NonNull;

import org.ifinalframework.data.annotation.Table;
import org.ifinalframework.util.Asserts;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author iimik
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

    public BaseEntity(final ProcessingEnvironment processEnv, final TypeElement typeElement) {

        Elements elements = processEnv.getElementUtils();
        this.typeElement = typeElement;
        this.packageName = elements.getPackageOf(typeElement).toString();
        this.name = typeElement.getQualifiedName().toString();
        this.table = initTable();
        this.simpleName = typeElement.getSimpleName().toString();
        Types types = processEnv.getTypeUtils();
        this.type = types.erasure(typeElement.asType()).toString();

    }

    private String initTable() {
        Table annotation = getAnnotation(Table.class);
        return Asserts.isNull(annotation) ? this.typeElement.getSimpleName().toString() : annotation.value()[0];
    }

    @Override
    public void addProperty(final Property property) {

        if (property.isIdProperty()) {
            if (idProperty != null) {
                throw new IllegalArgumentException(
                        "the entity must only have only one id property!,entity=" + getType());
            }
            this.idProperty = property;
        }

        if (propertyCache.containsKey(property.getName())) {
            throw new IllegalArgumentException(
                    String.format("the entity have not only one property named %s", property.getName()));
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
    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public Property getProperty(final String name) {

        return propertyCache.get(name);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Property getIdProperty() {
        return idProperty;
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {

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
