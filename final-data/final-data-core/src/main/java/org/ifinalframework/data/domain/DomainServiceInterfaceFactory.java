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

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;

/**
 * DomainServiceInterfaceFactory.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
public class DomainServiceInterfaceFactory {

    private final ClassPool classPool = ClassPool.getDefault();
    private final CtClass domainService = classPool.get(DomainService.class.getName());

    public DomainServiceInterfaceFactory() throws NotFoundException {
    }

    public Class<? extends DomainService> create(Class<?> idClass, Class<?> entityClass) throws CannotCompileException {

        SignatureAttribute.ClassSignature ac = new SignatureAttribute.ClassSignature(null, null,
                // Set interface and its generic params
                new SignatureAttribute.ClassType[]{
                        new SignatureAttribute.ClassType(DomainService.class.getName(),
                                new SignatureAttribute.TypeArgument[]{
                                        new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(idClass.getName())),
                                        new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(entityClass.getName()))
                                }
                        )});


        final String domainServiceName = String.join(".",
                DomainNameHelper.servicePackage(entityClass), entityClass.getSimpleName() + DomainService.class.getSimpleName());
        final CtClass ctClass = classPool.makeInterface(domainServiceName, domainService);
        ctClass.setGenericSignature(ac.encode());
        return (Class<? extends DomainService>) ctClass.toClass();
    }
}
