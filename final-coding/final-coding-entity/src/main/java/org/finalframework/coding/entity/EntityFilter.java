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

package org.finalframework.coding.entity;


import org.finalframework.core.filter.Filter;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.annotation.IEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 19:48:51
 * @since 1.0
 */
public class EntityFilter implements Filter<TypeElement> {

    /**
     * @see IEntity
     */
    private final TypeElement entityTypeElement;
    private final Types typeUtils;

    /**
     * @see org.finalframework.data.annotation.Transient
     */
    @Nullable
    private final Class<? extends Annotation> transientAnnotation;
    private final Messager messager;

    public EntityFilter(@NonNull ProcessingEnvironment processingEnvironment) {
        this(processingEnvironment, IEntity.class, Transient.class);
    }


    public EntityFilter(@NonNull ProcessingEnvironment processingEnvironment, @NonNull Class<?> entityClass, @Nullable Class<? extends Annotation> transientAnnotation) {
        this.typeUtils = processingEnvironment.getTypeUtils();
        this.messager = processingEnvironment.getMessager();
        this.transientAnnotation = transientAnnotation;
        this.entityTypeElement = processingEnvironment.getElementUtils().getTypeElement(entityClass.getCanonicalName());
    }

    @Override
    public boolean matches(TypeElement typeElement) {
        // 忽略抽象的类
        if (typeElement.getModifiers().contains(Modifier.ABSTRACT)) {
            return false;
        }
        //忽略被注解不解析的类
        if (transientAnnotation != null && typeElement.getAnnotation(transientAnnotation) != null) {
            return false;
        }

        boolean subtype = typeUtils.isSubtype(typeUtils.erasure(typeElement.asType()), typeUtils.erasure(entityTypeElement.asType()));
        if (subtype) {
            String msg = "[INFO] [EntityFilter] find entity : " + typeElement.getQualifiedName().toString();
            System.out.println(msg);
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
        return subtype;
    }


}

