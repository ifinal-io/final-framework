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

package org.finalframework.util;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ReflectionsTest {

    @Test
    void findAnnotationAttributes() {
        final Method method = Reflections.findRequiredMethod(ReflectionTarget.class, "method");
        Assertions.assertNotNull(method, "not found method of method");
        final AnnotationAttributes annotationAttributes = Reflections.findAnnotationAttributes(method, Cacheable.class);
        logger.info("find annotation attributes: {}", annotationAttributes);
        Assertions.assertNotNull(annotationAttributes);
    }

}
