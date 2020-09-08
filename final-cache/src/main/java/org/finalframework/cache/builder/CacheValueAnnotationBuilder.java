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


import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.cache.operation.CacheValueOperation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 14:26:21
 * @since 1.0
 */
@SpringComponent
public class CacheValueAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CacheValue, CacheValueOperation> {
    @Override
    public CacheValueOperation build(Class<?> type, CacheValue ann) {
        return null;
    }

    @Override
    public CacheValueOperation build(Method method, CacheValue ann) {
        return null;
    }

    @Override
    public CacheValueOperation build(Integer index, Parameter parameter, Type parameterType, CacheValue ann) {
        return CacheValueOperation.builder()
                .name(parameter.toString())
                .index(index)
                .parameter(parameter)
                .parameterType(parameterType)
                .key(parse(ann.key(), ann.delimiter()))
                .field(parse(ann.field(), ann.delimiter()))
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();
    }
}
