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

import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;

import org.springframework.lang.NonNull;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-01 21:44:30
 * @since 1.0
 */
public class Annotations {

    private final ProcessingEnvironment processEnv;

    public Annotations(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
    }

    public static boolean isAnnotationPresent(@NonNull Element element, @NonNull Class<? extends Annotation> ann) {
        if (element.getAnnotation(ann) != null) {
            return true;
        }
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (annotationType.asElement().getAnnotation(ann) != null) {
                return true;
            }
        }

        return false;

    }
}
