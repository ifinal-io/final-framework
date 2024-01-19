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

package org.ifinalframework.context.result.consumer;


import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.result.ResultConsumer;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.util.JarVersions;

import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * VersionResultConsumer
 *
 * @author iimik
 * @since 1.5.6
 **/
@Slf4j
@Component
public class VersionResultConsumer implements ResultConsumer<Object>, ApplicationContextAware, SmartInitializingSingleton {

    @Setter
    private ApplicationContext applicationContext;
    private String applicationVersion;

    @Override
    public void accept(Result<Object> result) {
        result.setVersion(applicationVersion);
    }

    @Override
    public boolean test(Result<?> result) {
        return true;
    }

    @Override
    public void afterSingletonsInstantiated() {
        final Map<String, Object> springBootApplications = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);

        if (springBootApplications.size() == 1) {
            final Object springBootApplication = springBootApplications.values().stream().findFirst().get();
            final Class<?> targetClass = AopUtils.getTargetClass(springBootApplication);
            final String version = JarVersions.getVersion(targetClass);

            logger.info("{} version: {}", targetClass.getSimpleName(), version);

            this.applicationVersion = version;
        }


    }
}
