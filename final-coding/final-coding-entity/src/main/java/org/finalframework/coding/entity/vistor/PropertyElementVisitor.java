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

package org.finalframework.coding.entity.vistor;


import org.finalframework.coding.entity.Property;
import org.finalframework.coding.entity.filter.PropertyExecutableElementFilter;
import org.finalframework.coding.entity.filter.PropertyVariableElementFilter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor8;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:08:51
 * @see PropertyVariableElementFilter
 * @since 1.0
 */
public class PropertyElementVisitor extends SimpleElementVisitor8<Property, TypeElement> {

    private PropertyVariableElementFilter propertyVariableElementFilter = new PropertyVariableElementFilter();
    private PropertyExecutableElementFilter propertyExecutableElementFilter = new PropertyExecutableElementFilter();

    @Override
    public Property visitVariable(VariableElement e, TypeElement entity) {

        if (!propertyVariableElementFilter.matches(e)) return null;
        // this element must be a field.
        return null;
    }


    @Override
    public Property visitExecutable(ExecutableElement e, TypeElement typeElement) {
        return null;
    }
}

