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

package org.finalframework.coding.spring.factory;


import org.finalframework.coding.annotation.Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:28:20
 * @since 1.0
 */
@Template("spring/spring.factories.vm")
public class SpringFactories implements Serializable {

    private MultiValueMap<String, String> springFactories = new LinkedMultiValueMap<>();

    public void addSpringFactories(TypeElement clazz, Collection<TypeElement> elements) {
        elements.forEach(item -> addSpringFactory(clazz, item));
    }

    public void addSpringFactory(TypeElement clazz, TypeElement element) {
        this.addSpringFactory(clazz.getQualifiedName().toString(), element.getQualifiedName().toString());
    }

    public void addSpringFactory(String factoryClass, String factoryName) {
        List<String> factories = this.springFactories.get(factoryClass);
        if (factories == null || !factories.contains(factoryName)) {
            this.springFactories.add(factoryClass, factoryName);
        }
    }

    public MultiValueMap<String, String> getSpringFactories() {
        return springFactories;
    }

}

