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

import org.ifinalframework.util.stream.Streamable;

import javax.lang.model.element.TypeElement;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Entity extends Streamable<Property>, Iterable<Property> {

    String getPackage();

    String getName();

    String getTable();

    String getSimpleName();

    TypeElement getElement();

    String getRawType();

    List<Property> getProperties();

    default Property getRequiredProperty(String name) {

        Property property = getProperty(name);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of %s not found for %s!", name, getType()));
    }

    Property getProperty(String name);

    String getType();

    default Property getRequiredIdProperty() {

        Property property = getIdProperty();

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required identifier property not found for %s!", getType()));
    }

    Property getIdProperty();

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

}
