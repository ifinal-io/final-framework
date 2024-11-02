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

package org.ifinalframework.data.auto.validator;

import org.ifinalframework.util.function.Filter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumValidator extends SimpleElementVisitor8<Void, Void> implements
        Filter<TypeElement> {

    private final ProcessingEnvironment processingEnvironment;

    private final Class<?> enumInterface;

    public EnumValidator(final ProcessingEnvironment processingEnvironment, final Class<?> enumInterface) {

        this.processingEnvironment = processingEnvironment;
        this.enumInterface = enumInterface;
    }

    @Override
    public Void visitType(final TypeElement e, final Void param) {

        if (matches(e)) {
            validate(e, enumInterface);
        }
        return super.visitType(e, param);
    }

    @Override
    public boolean matches(final TypeElement typeElement) {

        return ElementKind.ENUM == typeElement.getKind();
    }

    public Void validate(final TypeElement typeElement, final Class<?> enumInterface) {

        if (!isAssignable(typeElement, enumInterface)) {
            processingEnvironment.getMessager()
                    .printMessage(Kind.ERROR,
                            "the enum type of " + typeElement.getQualifiedName().toString()
                                    + " must be implements the interface of " + enumInterface
                                    .getCanonicalName());
        }
        return null;
    }

    private boolean isAssignable(final TypeElement element, final Class<?> clazz) {

        Types typeUtils = processingEnvironment.getTypeUtils();
        TypeElement typeElement = processingEnvironment.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return typeUtils.isSubtype(typeUtils.erasure(element.asType()), typeUtils.erasure(typeElement.asType()));
    }

}
