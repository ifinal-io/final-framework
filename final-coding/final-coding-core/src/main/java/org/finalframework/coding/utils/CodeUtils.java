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

package org.finalframework.coding.utils;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 01:29:35
 * @since 1.0
 */
public class CodeUtils {
    private final Elements elements;
    private final Types types;

    public CodeUtils(ProcessingEnvironment pe) {
        this.elements = pe.getElementUtils();
        this.types = pe.getTypeUtils();
    }

    public static VariableElement findField(@NonNull TypeElement clazz, @NonNull String fieldName, @Nullable TypeMirror type) {
        return ElementFilter.fieldsIn(clazz.getEnclosedElements())
                .stream()
                .filter(it -> it.getSimpleName().toString().contains(fieldName))
                .findFirst()
                .get();
    }

    public static List<VariableElement> fields(TypeElement element) {
        return ElementFilter.fieldsIn(element.getEnclosedElements());
    }

    public static List<ExecutableElement> methods(TypeElement element) {
        return ElementFilter.methodsIn(element.getEnclosedElements());
    }
}

