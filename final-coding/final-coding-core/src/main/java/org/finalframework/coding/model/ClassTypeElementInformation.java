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

package org.finalframework.coding.model;


import org.finalframework.coding.utils.CodeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 13:25:29
 * @since 1.0
 */
public class ClassTypeElementInformation implements TypeElementInformation<TypeElement> {
    private final TypeElement element;

    public ClassTypeElementInformation(TypeElement element) {
        this.element = element;
    }

    @Override
    public Element getElement() {
        return this.element;
    }

    public List<VariableElement> getFields() {
        return CodeUtils.fields(element);
    }

    public List<ExecutableElement> getMethods() {
        return CodeUtils.methods(element);
    }
}

