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

package org.ifinalframework.data.auto.processor;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.util.function.Filter;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import java.util.Objects;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class TypeElementFilter implements Filter<TypeElement> {

    private final Types types;

    private final Messager messager;

    private final TypeElement entityTypeElement;

    private final TypeElement transientAnnotationTypeElement;

    public TypeElementFilter(final @NonNull ProcessingEnvironment processingEnvironment,
                             final @NonNull TypeElement entityTypeElement,
                             final @Nullable TypeElement transientAnnotationTypeElement) {

        Objects.requireNonNull(entityTypeElement, "typeElement can not be null!");

        this.types = processingEnvironment.getTypeUtils();
        this.messager = processingEnvironment.getMessager();
        this.entityTypeElement = entityTypeElement;
        this.transientAnnotationTypeElement = transientAnnotationTypeElement;
    }

    @Override
    public boolean matches(final TypeElement typeElement) {
        //忽略被注解不解析的类
        if (isAnnotated(typeElement, transientAnnotationTypeElement)) {
            return false;
        }
        messager
                .printMessage(Diagnostic.Kind.NOTE, String.format("[INFO] [TypeElementFilter] filter typeElement: %s",
                        typeElement.getQualifiedName().toString()));
        final boolean subtype = types
                .isSubtype(types.erasure(typeElement.asType()), types.erasure(entityTypeElement.asType()));
        if (subtype) {
            final String msg = "[INFO] [EntityFilter] find entity : " + typeElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
        return subtype;
    }

    private boolean isAnnotated(final @NonNull TypeElement element, final @Nullable TypeElement annotationTypeElement) {

        if (Objects.isNull(annotationTypeElement)) {
            return false;
        }

        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (types.isSameType(annotationMirror.getAnnotationType(), annotationTypeElement.asType())) {
                return true;
            }
        }

        return false;

    }

}

