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

package org.ifinalframework.data.auto.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import java.util.List;
import java.util.Objects;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class SetterAndGetterFilter {

    private static final Logger logger = LoggerFactory.getLogger(SetterAndGetterFilter.class);

    public SetterAndGetterFilter(final ProcessingEnvironment env) {

    }

    public boolean matches(final ExecutableElement method, final TypeMirror parameterTypeOrReturnType) {

        if (method.getKind() != ElementKind.METHOD) {
            return false;
        }

        if (Objects.nonNull(parameterTypeOrReturnType)) {
            // check
            logger.info("try to check method parameterTypeOrReturnType");
        }

        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters) || isGetter(name, parameters);
    }

    private boolean isSetter(final String name, final List<? extends VariableElement> parameters) {

        return parameters.size() == 1 && name.startsWith(Bean.SET_PREFIX);
    }

    public boolean isSetter(final ExecutableElement method) {

        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters);
    }

    private boolean isGetter(final String name, final List<? extends VariableElement> parameters) {

        return parameters.isEmpty() && (name.startsWith(Bean.GET_PREFIX) || name.startsWith(Bean.IS_PREFIX));
    }

    public boolean isGetter(final ExecutableElement method) {

        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isGetter(name, parameters);
    }

}

