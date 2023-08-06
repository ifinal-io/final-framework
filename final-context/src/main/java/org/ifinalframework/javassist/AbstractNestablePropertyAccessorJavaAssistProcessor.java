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

package org.ifinalframework.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.core.convert.TypeDescriptor;

import org.ifinalframework.auto.service.annotation.AutoService;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractNestablePropertyAccessorJavaAssistProcessor.
 *
 * @author ilikly
 * @version 1.5.0
 * @see AbstractNestablePropertyAccessor
 * @see org.ifinalframework.beans.BeanTypeDescriptorFactory
 * @since 1.5.0
 */
@Slf4j
@AutoService(JavaAssistProcessor.class)
public class AbstractNestablePropertyAccessorJavaAssistProcessor implements JavaAssistProcessor {
    @Override
    public void process(ClassPool classPool) throws Throwable {

        logger.info("start process org.springframework.beans.AbstractNestablePropertyAccessor");


        final CtClass ctClass = classPool.get("org.springframework.beans.AbstractNestablePropertyAccessor");

        if (ctClass.isFrozen()) {
            return;
        }

        final CtField list = CtField.make("private java.util.List beanTypeDescriptorFactories "
                + "= org.springframework.core.io.support.SpringFactoriesLoader.loadFactories("
                + "org.ifinalframework.beans.BeanTypeDescriptorFactory.class,getClass().getClassLoader());", ctClass);
        ctClass.addField(list);


        /**
         * @see AbstractNestablePropertyAccessor#newValue(Class, TypeDescriptor, String)
         * @see org.ifinalframework.beans.BeanTypeDescriptorFactory#create(Class, TypeDescriptor)
         */
        final CtMethod newValue = ctClass.getDeclaredMethod("newValue");
        newValue.insertBefore(
                "for (int i = 0; i < beanTypeDescriptorFactories.size(); i++) {\n"
                        + "    org.ifinalframework.beans.BeanTypeDescriptorFactory beanTypeDescriptorFactory "
                        + "         = beanTypeDescriptorFactories.get(i);\n"
                        + "    if (beanTypeDescriptorFactory.support($1, $2)) {\n"
                        + "        return beanTypeDescriptorFactory.create($1, $2);\n"
                        + "    }\n"
                        + "}");

        final Class<?> aClass = ctClass.toClass(BeanWrapper.class);

        logger.info("process success org.springframework.beans.AbstractNestablePropertyAccessor");
    }
}
