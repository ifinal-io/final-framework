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


import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.operation.CachePutOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
@SpringComponent
public class CachePutAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CachePut, CachePutOperation> {

    @Override
    public CachePutOperation build(Class<?> type, CachePut ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CachePutOperation build(Method method, CachePut ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CachePutOperation build(AnnotatedElement ae, CachePut ann) {
        return CachePutOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .field(parse(ann.field(), ann.delimiter()))
                .delimiter(ann.delimiter())
                .value(ann.value())
                .condition(ann.condition())
                .point(ann.point())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
