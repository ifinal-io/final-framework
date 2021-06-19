/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.dubbo.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.AbstractInterfaceConfig;

/**
 * DubboProviderApplicationContext.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class DubboProviderApplicationContext {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:spring-dubbo-provider.xml");

//        HelloService helloService = context.getBean(HelloService.class);

        final ObjectProvider<AbstractInterfaceConfig> beanProvider = context
            .getBeanProvider(AbstractInterfaceConfig.class);
        final List<AbstractInterfaceConfig> serviceBeans = beanProvider.stream().collect(Collectors.toList());

        int i = 0;
        while (true) {

            logger.info("loop {}", i++);

            Thread.sleep(5000);
        }
    }

}
