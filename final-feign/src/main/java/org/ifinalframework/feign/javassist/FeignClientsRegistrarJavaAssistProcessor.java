/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.feign.javassist;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.openfeign.FeignClient;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.javassist.JavaAssistProcessor;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * FeignClientsRegistrarJavaAssistProcessor
 *
 * @author iimik
 * @see org.springframework.cloud.openfeign.FeignClientsRegistrar
 * @see org.ifinalframework.feign.bean.factory.FeignClientBeanDefinitionPostProcessor
 * @since 1.6.0
 **/
@Slf4j
@AutoService(JavaAssistProcessor.class)
public class FeignClientsRegistrarJavaAssistProcessor implements JavaAssistProcessor {
    @Override
    public void process(ClassPool classPool) throws Throwable {
        final CtClass ctClass = classPool.getCtClass("org.springframework.cloud.openfeign.FeignClientsRegistrar");

        if (ctClass.isFrozen()) {
            return;
        }

        processRegisterClientConfigurationMethod(ctClass);
        processGetClientNameMethod(ctClass);

        ctClass.toClass(FeignClient.class);

    }

    /**
     * {@link org.springframework.cloud.openfeign.FeignClientsRegistrar#registerClientConfiguration(BeanDefinitionRegistry, Object, Object, Object)}方法体之前，插入以下代码
     * <pre class="code">
     *      if(name == null){
     *          name = className;
     *      }
     * </pre>
     *
     * @param ctClass
     * @see org.ifinalframework.feign.bean.factory.FeignClientBeanDefinitionPostProcessor
     */
    private void processRegisterClientConfigurationMethod(CtClass ctClass) throws CannotCompileException, NotFoundException {
        final CtMethod registerClientConfiguration = ctClass.getDeclaredMethod("registerClientConfiguration");
        registerClientConfiguration.insertBefore("""
                    if($2 == null) {
                        $2 = $3;
                    }
                """);
    }

    /**
     * 修改{@link org.springframework.cloud.openfeign.FeignClientsRegistrar#getClientName(Map)} 方法，使其不再抛出异常
     *
     * @param ctClass
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    private void processGetClientNameMethod(CtClass ctClass) throws NotFoundException, CannotCompileException {
        final CtMethod getClientName = ctClass.getDeclaredMethod("getClientName");

        getClientName.setBody("""
                    {
                        if ($1 == null) {
                			return null;
                		}
                		String value = (String) $1.get("contextId");
                		if (!org.springframework.util.StringUtils.hasText(value)) {
                			value = (String) $1.get("value");
                		}
                		if (!org.springframework.util.StringUtils.hasText(value)) {
                			value = (String) $1.get("name");
                		}
                		if (!org.springframework.util.StringUtils.hasText(value)) {
                			value = (String) $1.get("serviceId");
                		}
                		if (!org.springframework.util.StringUtils.hasText(value)) {
                			value = (String) $1.get("className");
                		}
                		return null;
                    }
                """);
    }
}
