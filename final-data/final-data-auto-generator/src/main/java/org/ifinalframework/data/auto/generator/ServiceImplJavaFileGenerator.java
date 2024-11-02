/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.auto.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.service.AbsServiceImpl;
import org.ifinalframework.javapoets.JavaPoets;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * Build a {@link JavaFile} which extends to the class of {@link AbsServiceImpl}.
 * <pre class="code">
 * interface EntityServiceImpl extends AbsServiceImpl&lt;Long,Entity&gt;{
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
public class ServiceImplJavaFileGenerator implements JavaFileGenerator<AutoService> {
    @Override
    public String getName(@NonNull AutoService ann, @NonNull Class<?> clazz) {
        return String.join(".", DomainNameHelper.servicePackage(clazz) + ".impl", DomainNameHelper.serviceName(clazz) + "Impl");
    }

    @NonNull
    @Override
    public JavaFile generate(@NonNull AutoService ann, @NonNull Class<?> clazz) {

        String servicePackage = DomainNameHelper.servicePackage(clazz);
        String serviceName = DomainNameHelper.serviceName(clazz);

        final String serviceImplPackage = servicePackage + ".impl";
        final String serviceImplName = serviceName + "Impl";

        logger.info("start generate service for entity of {}.{}", serviceImplPackage, serviceImplName);

        try {

            Class<?> id = ResolvableType.forClass(clazz).as(IEntity.class).getGeneric().resolve();

            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("not found id for entity of " + clazz);
            }
            // AbsServiceImpl<I,IEntity>
            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(AbsServiceImpl.class),
                    ClassName.get(id),
                    ClassName.get(clazz)
            );

            //  class EntityServiceImpl extends AbsServiceImpl<I, IEntity> implements EntityService
            TypeSpec service = TypeSpec.classBuilder(serviceImplName)
                    .superclass(parameterizedTypeName)
                    .addSuperinterface(ClassName.get(servicePackage, serviceName))
                    .addAnnotation(Service.class)
                    .addAnnotation(JavaPoets.generated(getClass()))
                    .addJavadoc(JavaPoets.Javadoc.author())
                    .addJavadoc(JavaPoets.Javadoc.version())
                    .build();

            return JavaFile.builder(serviceImplPackage, service)
                    .skipJavaLangImports(true)
                    .indent("    ")
                    .build();

        } finally {
            logger.info("generated service: {}.{}", serviceImplPackage, serviceImplName);
        }
    }
}


