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

package org.ifinalframework.data.cache;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import org.ifinalframework.cache.annotation.Cacheable;
import org.ifinalframework.util.Reflections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class CacheAnnotationAttributeProcessorTest {

    private final CacheAnnotationAttributeProcessor processor = new CacheAnnotationAttributeProcessor();

    @Test
    void cacheable() {
        Method cacheable = Reflections.findRequiredMethod(CacheServiceImpl.class, "cacheable", Integer.class);

        Set<Cacheable> annotations = AnnotatedElementUtils.findAllMergedAnnotations(cacheable, Cacheable.class);

        Assertions.assertTrue(annotations.size() > 0);

        for (Cacheable annotation : annotations) {
            AnnotationAttributes annotationAttributes = Reflections.getAnnotationAttributes(annotation);
            processor.doProcess(cacheable, annotationAttributes);
            logger.info("find cacheable annotation attributes: {}", annotationAttributes);
        }

    }

}
