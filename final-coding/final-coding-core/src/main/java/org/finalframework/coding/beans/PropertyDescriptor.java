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


import org.springframework.data.util.Lazy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 00:59:25
 * @since 1.0
 */
public class PropertyDescriptor {

    private final Bean bean;
    private final String name;
    private final Optional<VariableElement> field;
    private final Optional<ExecutableElement> setter;
    private final Optional<ExecutableElement> getter;
    private final Lazy<String> toString;

    public PropertyDescriptor(Bean bean, String name, Optional<VariableElement> field,
                              Optional<ExecutableElement> setter,
                              Optional<ExecutableElement> getter) {
        this.bean = bean;
        this.name = name;
        this.field = field;
        this.setter = setter;
        this.getter = getter;

        this.toString = Lazy.of(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(":{field=").append(field.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",setter=").append(setter.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",getter=").append(getter.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append("}");
            return sb.toString();
        });
    }

    public Bean getBean() {
        return bean;
    }

    public String getName() {
        return name;
    }

    public Optional<VariableElement> getField() {
        return field;
    }

    public Optional<ExecutableElement> getSetter() {
        return setter;
    }

    public Optional<ExecutableElement> getGetter() {
        return getter;
    }

    @Override
    public String toString() {
        return toString.get();
    }
}

