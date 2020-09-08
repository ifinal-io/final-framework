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

package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationAnnotationParser;
import org.finalframework.spring.aop.OperationConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:01:35
 * @since 1.0
 */
public class AnnotationOperationSource extends AbsOperationSource {

    private final OperationAnnotationParser parser;

    public AnnotationOperationSource(Collection<Class<? extends Annotation>> annoTypes, OperationConfiguration configuration) {
        this.parser = new BaseOperationAnnotationParser(annoTypes, configuration);
    }

    @Override
    protected Collection<? extends Operation> findOperations(Method method) {
        final Collection<? extends Operation> collection = parser.parseOperationAnnotation(method);
        return Asserts.isEmpty(collection) ? null : collection;
    }

    @Override
    protected Collection<? extends Operation> findOperations(Class<?> clazz) {
        final Collection<? extends Operation> collection = parser.parseOperationAnnotation(clazz);
        return Asserts.isEmpty(collection) ? null : collection;
    }
}
