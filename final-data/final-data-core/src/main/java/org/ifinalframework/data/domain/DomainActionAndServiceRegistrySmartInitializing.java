/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.domain;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import org.ifinalframework.data.annotation.DomainResource;
import org.ifinalframework.data.domain.action.DefaultDomainActionsFactory;
import org.ifinalframework.data.domain.action.DomainActionRegistry;
import org.ifinalframework.data.domain.action.DomainActions;
import org.ifinalframework.data.domain.action.DomainActionsFactory;
import org.ifinalframework.data.domain.spi.LoggerAfterConsumer;
import org.ifinalframework.data.service.AbsService;
import org.ifinalframework.util.Proxies;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Objects;

import lombok.Setter;

/**
 * DomainActionAndServiceRegistrySmartInitializing
 *
 * @author iimik
 * @since 1.5.2
 **/
@Component
public class DomainActionAndServiceRegistrySmartInitializing implements ApplicationContextAware, SmartInitializingSingleton {
    @Setter
    private ApplicationContext applicationContext;

    @Resource
    private DomainServiceRegistry domainServiceRegistry;
    @Resource
    private DomainActionRegistry domainActionRegistry;
    @Resource
    private List<LoggerAfterConsumer> loggerAfterConsumers;

    @Override
    public void afterSingletonsInstantiated() {
        String userClassName = applicationContext.getEnvironment().getRequiredProperty("final.security.user-class");

        Class<?> userClass = ClassUtils.resolveClassName(userClassName, getClass().getClassLoader());

        final DomainActionsFactory domainActionsFactory = new DefaultDomainActionsFactory(userClass, applicationContext,
                Proxies.composite(LoggerAfterConsumer.class, loggerAfterConsumers));

        applicationContext.getBeanProvider(AbsService.class).stream()
                .forEach(service -> {
                    Class<?> entityClass = ResolvableType.forClass(AopUtils.getTargetClass(service)).as(AbsService.class).resolveGeneric(1);
                    final DomainResource domainResource = AnnotationUtils.findAnnotation(entityClass, DomainResource.class);

                    if (Objects.nonNull(domainResource)) {


                        final DomainActions domainActions = domainActionsFactory.create(service);
                        DomainService domainService = new DefaultDomainService(domainActions);
                        for (final String resource : domainResource.value()) {
                            domainActionRegistry.registry(resource, domainActions);
                            domainServiceRegistry.registry(resource, domainService);
                        }
                    }


                });
    }
}
