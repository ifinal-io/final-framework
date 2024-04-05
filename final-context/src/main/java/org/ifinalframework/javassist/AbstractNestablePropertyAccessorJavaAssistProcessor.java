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

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.core.convert.TypeDescriptor;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.beans.BeanTypeDescriptorFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractNestablePropertyAccessorJavaAssistProcessor.
 *
 * @author iimik
 * @version 1.5.0
 * @see AbstractNestablePropertyAccessor
 * @see BeanTypeDescriptorFactory
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

        addBeanTypeDescriptorFactories(ctClass);
        processNewValue(ctClass);

        final Class<?> aClass = ctClass.toClass(BeanWrapper.class);

        logger.info("process success org.springframework.beans.AbstractNestablePropertyAccessor");
    }

    /**
     *
     * 插入一个{@link BeanTypeDescriptorFactory}集合字段，通过Spring Spi扩展点自动加载。
     * <pre class="code">
     *  private List&lt;BeanTypeDescriptorFactory&gt; beanTypeDescriptorFactories = SpringFactoriesLoader.loadFactories(BeanTypeDescriptorFactory.class, getClass().getClassLoader());
     * </pre>
     * @param ctClass
     * @throws Throwable
     */
    private void addBeanTypeDescriptorFactories(CtClass ctClass) throws Throwable {
        final CtField beanTypeDescriptorFactories = CtField.make("private java.util.List beanTypeDescriptorFactories "
                + "= org.springframework.core.io.support.SpringFactoriesLoader.loadFactories("
                + "org.ifinalframework.beans.BeanTypeDescriptorFactory.class,getClass().getClassLoader());", ctClass);
        ctClass.addField(beanTypeDescriptorFactories);
    }


    /**
     * 修改{@link AbstractNestablePropertyAccessor#newValue(Class, TypeDescriptor, String)}方法体之前，插入一段代码，
     * 优先遍历调用{@link BeanTypeDescriptorFactory#create(Class, TypeDescriptor)}方法
     * @see #addBeanTypeDescriptorFactories(CtClass)
     * @see AbstractNestablePropertyAccessor#newValue(Class, TypeDescriptor, String)
     * @see BeanTypeDescriptorFactory#create(Class, TypeDescriptor)
     */
    private void processNewValue(CtClass ctClass) throws NotFoundException, CannotCompileException {

        final CtMethod newValue = ctClass.getDeclaredMethod("newValue");
        newValue.insertBefore("for (int i = 0; i < beanTypeDescriptorFactories.size(); i++) {\n"
                + "    org.ifinalframework.beans.BeanTypeDescriptorFactory beanTypeDescriptorFactory "
                + "         = beanTypeDescriptorFactories.get(i);\n"
                + "    if (beanTypeDescriptorFactory.support($1, $2)) {\n"
                + "        return beanTypeDescriptorFactory.create($1, $2);\n"
                + "    }\n" + "}");
    }
}
