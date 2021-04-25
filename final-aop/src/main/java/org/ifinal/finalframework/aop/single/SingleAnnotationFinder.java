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
 *
 */

package org.ifinal.finalframework.aop.single;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Set;

import org.ifinal.finalframework.aop.AnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleAnnotationFinder<A extends Annotation> implements AnnotationFinder<Collection<A>>, Serializable {

    private static final long serialVersionUID = -2544023527538343148L;

    private final Class<A> ann;

    private final boolean repeatable;

    @SuppressWarnings("unused")
    private SingleAnnotationFinder(final Class<A> ann, final boolean repeatable) {

        this.ann = ann;
        this.repeatable = repeatable;
    }

    public SingleAnnotationFinder(final Class<A> ann) {

        this.ann = ann;
        this.repeatable = ann.getAnnotation(Repeatable.class) != null;
    }

    @Override
    public Collection<A> findAnnotations(final @NonNull AnnotatedElement ae) {

        if (repeatable) {
            return AnnotatedElementUtils.findMergedRepeatableAnnotations(ae, ann);
        } else {
            final Set<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
            if (annotations.size() > 1) {
                // More than one annotation found -> local declarations override interface-declared ones...
                return AnnotatedElementUtils.getAllMergedAnnotations(ae, ann);
            }
            return annotations;
        }
    }

}
