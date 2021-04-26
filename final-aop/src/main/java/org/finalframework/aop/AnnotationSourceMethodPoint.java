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

package org.finalframework.aop;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class AnnotationSourceMethodPoint extends StaticMethodMatcherPointcut {

    private final AnnotationSource<?> source;

    public AnnotationSourceMethodPoint(final AnnotationSource<?> source) {

        this.source = source;
    }

    @Override
    public boolean matches(final @NonNull Method method, final @NonNull Class<?> targetClass) {

        final Object annotations = source.getAnnotations(method, targetClass);

        final boolean matches = Asserts.nonEmpty(annotations) && !Boolean.FALSE.equals(annotations);
        if (matches) {
            logger.info("matched -> targetClass={},method={},annotations={}", targetClass, method, annotations);
        }
        return matches;
    }

}
