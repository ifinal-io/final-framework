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

import org.ifinalframework.data.auto.beans.Bean;
import org.ifinalframework.data.auto.beans.PropertyDescriptor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class EntityFactory {

    private static final Map<String, Entity> cache = new HashMap<>();

    private EntityFactory() {

    }

    public static Entity create(final ProcessingEnvironment processEnv, final TypeElement element) {

        TypeElement typeElement = element;

        String name = typeElement.getQualifiedName().toString();

        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        final BaseEntity result = new BaseEntity(processEnv, typeElement);

        while (typeElement != null) {

            Bean.from(processEnv, typeElement).stream()
                    .filter(EntityFactory::isProperty
                    )
                    .map(property -> new AnnotationProperty(processEnv, property.getField(), Optional.of(property)))
                    .forEach(result::addProperty);

            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass != null) {
                if (superclass.getKind() == TypeKind.NONE) {
                    typeElement = null;
                } else {
                    typeElement = (TypeElement) ((DeclaredType) superclass).asElement();
                }
            }
        }

        cache.put(name, result);

        return result;
    }

    private static boolean isProperty(PropertyDescriptor property) {
        if (property.getField().isPresent()) {
            return property.getField().map(field ->
                            !(field.getModifiers().contains(Modifier.FINAL) || field.getModifiers()
                                    .contains(Modifier.STATIC)))
                    .orElse(true);
        } else if (property.getGetter().isPresent()) {
            return property.getGetter().map(method ->
                    !(method.getModifiers().contains(Modifier.FINAL) || method.getModifiers()
                            .contains(Modifier.STATIC))).orElse(true);
        }

        return false;
    }

}
