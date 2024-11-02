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

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.service.AbsService;
import org.ifinalframework.javapoets.JavaPoets;

import javax.lang.model.element.Modifier;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceJavaFileGenerator.
 * <pre class="code">
 * public interface EntityService extends AbsService&lt;Long,Entity&gt;{
 *
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
public class ServiceJavaFileGenerator implements JavaFileGenerator<AutoService> {
    @Override
    public String getName(AutoService ann, Class<?> clazz) {
        return String.join(".", DomainNameHelper.servicePackage(clazz), DomainNameHelper.serviceName(clazz));
    }

    @NonNull
    @Override
    public JavaFile generate(@NonNull AutoService ann, @NonNull Class<?> clazz) {

        String servicePackage = DomainNameHelper.servicePackage(clazz);
        String serviceName = DomainNameHelper.serviceName(clazz);

        logger.info("start generate service for entity of {}.{}", servicePackage, serviceName);

        try {

            Class<?> id = ResolvableType.forClass(clazz).as(IEntity.class).getGeneric().resolve();

            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("not found id for entity of " + clazz);
            }

            // AbsService<I,IEntity>
            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(AbsService.class),
                    ClassName.get(id),
                    ClassName.get(clazz)
            );

            // public interface EntityService extends AbsService<I,IEntity>
            TypeSpec service = TypeSpec.interfaceBuilder(serviceName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(parameterizedTypeName)
                    .addAnnotation(JavaPoets.generated(getClass()))
                    .addJavadoc(JavaPoets.Javadoc.author())
                    .addJavadoc(JavaPoets.Javadoc.version())
                    .build();

            return JavaFile.builder(servicePackage, service)
                    .skipJavaLangImports(true).build();
        } finally {
            logger.info("generated service: {}.{}", servicePackage, serviceName);
        }
    }
}


