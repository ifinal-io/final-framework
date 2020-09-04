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


import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.operation.CacheLockOperation;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
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
public class CacheLockAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CacheLock, CacheLockOperation> {

    @Override
    public CacheLockOperation build(Class<?> type, CacheLock ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheLockOperation build(Method method, CacheLock ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheLockOperation build(AnnotatedElement ae, CacheLock ann) {
        return CacheLockOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .value(ann.value())
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .retry(ann.retry())
                .sleep(ann.sleep())
                .ttl(ann.ttl())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
