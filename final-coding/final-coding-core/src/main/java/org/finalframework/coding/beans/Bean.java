/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.coding.beans;


import org.finalframework.core.Streamable;
import org.finalframework.core.generator.NameGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.*;
import java.util.stream.Stream;

/**
 * start with {@link Bean#from(ProcessingEnvironment, TypeElement)}
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-15 13:50:18
 * @since 1.0
 */
public class Bean implements Streamable<PropertyDescriptor>, Iterable<PropertyDescriptor> {

    private static final Map<TypeElement, Bean> cache = new HashMap<>(128);

    static final String ADD_PREFIX = "add";
    static final String REMOVE_PREFIX = "remove";
    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";
    static final String IS_PREFIX = "is";

    private final ProcessingEnvironment env;
    private final TypeElement typeElement;
    private final SetterAndGetterFilter setterAndGetterFilter;

    private final Map<String, VariableElement> fields = new LinkedHashMap<>();
    private final List<ExecutableElement> methods = new ArrayList<>();
    private final Map<ExecutableElement, Boolean> processed = new HashMap<>();
    private final Map<String, PropertyDescriptor> properties = new LinkedHashMap<>();

    private Bean(ProcessingEnvironment env, TypeElement typeElement) {
        this.env = env;
        this.typeElement = typeElement;
        this.setterAndGetterFilter = new SetterAndGetterFilter(env);
        this.init();
    }

    public static Bean from(ProcessingEnvironment env, TypeElement typeElement) {

        if (!cache.containsKey(typeElement)) {
            synchronized (cache) {
                cache.put(typeElement, new Bean(env, typeElement));
            }
        }

        return cache.get(typeElement);
    }

    private void init() {
        List<? extends Element> elements = typeElement.getEnclosedElements();
        // init fields
        initFields(elements);
        // init methods
        initMethods(elements);
        // init properties
        initProperties();

    }

    private void initFields(List<? extends Element> elements) {
        ElementFilter.fieldsIn(elements).forEach(field -> fields.put(field.getSimpleName().toString(), field));
    }

    private void initMethods(List<? extends Element> elements) {
        ElementFilter.methodsIn(elements)
                .stream()
                .filter(method -> setterAndGetterFilter.matches(method, null))
                .forEach(methods::add);
    }

    private void initProperties() {

        // for each field
        fields.forEach((key, field) -> {
            this.properties.put(key, new PropertyDescriptor(this, key, Optional.of(field),
                    findSetter(key), findGetter(key)));
        });


        methods.stream()
                .filter(it -> !Boolean.TRUE.equals(processed.get(it)))
                .filter(setterAndGetterFilter::isSetter)
                .forEach(setter -> {
                    String setterName = setter.getSimpleName().toString();
                    if (setterName.startsWith(SET_PREFIX)) {
                        String propertyName = setterName.substring(SET_PREFIX.length());
                        if (!properties.containsKey(propertyName)) {
                            this.properties.put(propertyName, new PropertyDescriptor(this, propertyName, Optional.empty(),
                                    Optional.of(setter), findGetter(propertyName)));
                        }


                    }
                });

        methods.stream()
                .filter(it -> !Boolean.TRUE.equals(processed.get(it)))
                .filter(setterAndGetterFilter::isGetter)
                .forEach(getter -> {
                    String getterName = getter.getSimpleName().toString();
                    String propertyName;
                    if (getterName.startsWith(IS_PREFIX)) {
                        propertyName = NameGenerator.decapitalize(getterName, IS_PREFIX);
                    } else if (getterName.startsWith(GET_PREFIX)) {
                        propertyName = NameGenerator.decapitalize(getterName, GET_PREFIX);
                    } else {
                        throw new IllegalArgumentException("不支持的 getter 方法" + getter.toString());
                    }

                    if (!properties.containsKey(propertyName)) {
                        this.properties.put(propertyName, new PropertyDescriptor(this, propertyName, Optional.empty(),
                                findSetter(propertyName), Optional.of(getter)));
                    }

                });

    }

    private Optional<ExecutableElement> findSetter(String property) {
        String setterName = NameGenerator.capitalize(SET_PREFIX, property);
        Optional<ExecutableElement> setter = methods.stream()
                .filter(setterAndGetterFilter::isSetter)
                .filter(it -> it.getSimpleName().toString().equals(setterName))
                .findFirst();

        if (setter.isPresent()) {
            // TODO check parameter type
            processed.put(setter.get(), true);
        }

        return setter;
    }

    private Optional<ExecutableElement> findGetter(String property) {
        Optional<ExecutableElement> getter = methods.stream()
                .filter(setterAndGetterFilter::isGetter)
                .filter(it -> it.getSimpleName().toString()
                        .equals(NameGenerator.capitalize(GET_PREFIX, property)))
                .findFirst();

        if (getter.isPresent()) {
            // TODO check return type
            processed.put(getter.get(), true);
            return getter;
        }

        Optional<ExecutableElement> nextGetter = methods.stream()
                .filter(setterAndGetterFilter::isGetter)
                .filter(it -> it.getSimpleName().toString()
                        .equals(NameGenerator.capitalize(IS_PREFIX, property)))
                .findFirst();

        if (nextGetter.isPresent()) {
            // TODO check return type
            processed.put(nextGetter.get(), true);
            return nextGetter;
        }

        return Optional.empty();
    }


    @Override
    public Iterator<PropertyDescriptor> iterator() {
        return properties.values().iterator();
    }

    @Override
    public Stream<PropertyDescriptor> stream() {
        return properties.values().stream();
    }
}

