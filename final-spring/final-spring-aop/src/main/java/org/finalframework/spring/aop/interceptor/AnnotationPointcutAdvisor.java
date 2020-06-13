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


import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.data.util.Lazy;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:20:16
 * @since 1.0
 */
@SpringComponent
public class AnnotationPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationPointcutAdvisor.class);
    private final Lazy<Pointcut> pointcut;

    protected AnnotationPointcutAdvisor(OperationConfiguration configuration) {
        this.setAdvice(configuration.getInterceptor());
        this.pointcut = Lazy.of(configuration.getPointcut());
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut.get();
    }
}
