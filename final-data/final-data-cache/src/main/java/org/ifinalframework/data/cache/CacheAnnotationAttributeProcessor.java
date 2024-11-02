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

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import org.ifinalframework.cache.annotation.CacheDel;
import org.ifinalframework.cache.annotation.CacheIncrement;
import org.ifinalframework.cache.annotation.CacheLock;
import org.ifinalframework.cache.annotation.CacheValue;
import org.ifinalframework.cache.annotation.Cacheable;
import org.ifinalframework.core.annotation.AnnotationAttributesProcessor;
import org.ifinalframework.util.Asserts;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheAnnotationAttributeProcessor implements AnnotationAttributesProcessor {

    private static final String DELIMITER = ":";

    /**
     * @see Cacheable#key()
     * @see CacheIncrement#key()
     * @see CacheDel#key()
     * @see CacheLock#key()
     * @see CacheValue#key()
     */
    private static final String ATTRIBUTE_NAME_KEY = "key";

    /**
     * @see Cacheable#field()
     * @see CacheIncrement#field()
     * @see CacheValue#field()
     */
    private static final String ATTRIBUTE_NAME_FIELD = "field";

    private static final String ATTRIBUTE_NAME_DELIMITER = "delimiter";

    private static Collection<String> parse(final String[] keyOrField, final String delimiter) {

        if (Asserts.isEmpty(keyOrField)) {
            return Collections.emptyList();
        }
        final String split = getDelimiter(delimiter);
        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(split))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }

    private static String getDelimiter(final String delimiter) {

        return Asserts.isBlank(delimiter) ? DELIMITER : delimiter.trim();
    }

    @Override
    public void doProcess(final @NonNull AnnotatedElement annotatedElement,
                          final @NonNull AnnotationAttributes annotationAttributes) {

        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_KEY);
        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_FIELD);
    }

    private void processKeyOrField(final AnnotationAttributes annotationAttributes, final String name) {

        if (annotationAttributes.containsKey(name)) {
            Collection<String> strings = parse(annotationAttributes.getStringArray(name),
                    getDelimiter(annotationAttributes.getString(ATTRIBUTE_NAME_DELIMITER)));
            annotationAttributes.put(name, strings.toArray(new String[0]));
        }
    }

}
