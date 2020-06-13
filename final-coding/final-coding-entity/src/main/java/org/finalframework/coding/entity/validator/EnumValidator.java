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

package org.finalframework.coding.entity.validator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.finalframework.core.filter.Filter;
import org.finalframework.core.validator.ValidatorVisitor;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-17 21:39:17
 * @since 1.0
 */
public class EnumValidator extends SimpleElementVisitor8<Void, Void> implements
        ValidatorVisitor<Void, TypeElement, Class<?>>,
        Filter<TypeElement> {

    private final ProcessingEnvironment processingEnvironment;
    private final Class<?> enumInterface;

    public EnumValidator(ProcessingEnvironment processingEnvironment, Class<?> enumInterface) {
        this.processingEnvironment = processingEnvironment;
        this.enumInterface = enumInterface;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        if (matches(e)) {
            validate(e, enumInterface);
        }
        return super.visitType(e, aVoid);
    }

    @Override
    public boolean matches(TypeElement typeElement) {
        return ElementKind.ENUM == typeElement.getKind();
    }

    @Override
    public Void validate(TypeElement typeElement, Class<?> enumInterface) {

        if (!isAssignable(typeElement, enumInterface)) {
            processingEnvironment.getMessager()
                    .printMessage(Kind.ERROR,
                            "the enum type of " + typeElement.getQualifiedName().toString() + " must be implements the interface of " + enumInterface.getCanonicalName());
        }
        return null;
    }

    private boolean isAssignable(TypeElement element, Class<?> clazz) {
        Types typeUtils = processingEnvironment.getTypeUtils();
        TypeElement typeElement = processingEnvironment.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return typeUtils.isSubtype(typeUtils.erasure(element.asType()), typeUtils.erasure(typeElement.asType()));
    }
}
