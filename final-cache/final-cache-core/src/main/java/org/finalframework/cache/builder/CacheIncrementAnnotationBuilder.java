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

package org.finalframework.cache.builder;


import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.operation.CacheIncrementOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 22:54:34
 * @since 1.0
 */
@SpringComponent
public class CacheIncrementAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CacheIncrement, CacheIncrementOperation> {

    @Override
    public CacheIncrementOperation build(Class<?> type, CacheIncrement ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheIncrementOperation build(Method method, CacheIncrement ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheIncrementOperation build(AnnotatedElement ae, CacheIncrement ann) {
        final String delimiter = getDelimiter(ann.delimiter());
        return CacheIncrementOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), delimiter))
                .field(parse(ann.field(), delimiter))
                .delimiter(delimiter)
                .condition(ann.condition())
                .point(ann.point())
                .value(ann.value())
                .type(ann.type())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
