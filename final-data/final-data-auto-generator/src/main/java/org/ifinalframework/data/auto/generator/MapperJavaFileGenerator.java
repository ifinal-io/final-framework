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
import org.ifinalframework.data.auto.annotation.AutoMapper;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.javapoets.JavaPoets;

import org.apache.ibatis.annotations.Mapper;

import javax.lang.model.element.Modifier;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * AutoMapperJavaFileGenerator.
 * <pre class="code">
 * &#64;Mapper
 * public interface EntityMapper extends AbsMapper&lt;Long,Entity&gt;{
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
public class MapperJavaFileGenerator implements JavaFileGenerator<AutoMapper> {

    @Override
    public String getName(@NonNull AutoMapper ann, @NonNull Class<?> clazz) {
        return String.join(".", DomainNameHelper.mapperPackage(clazz), DomainNameHelper.mapperName(clazz));
    }

    @NonNull
    @Override
    public JavaFile generate(@NonNull AutoMapper ann, @NonNull Class<?> clazz) {

        final String mapperPackage = DomainNameHelper.mapperPackage(clazz);
        final String mapperName = DomainNameHelper.mapperName(clazz);
        try {
            logger.info("start generate mapper for clazz of {}", clazz);

            Class<?> id = ResolvableType.forClass(clazz).as(IEntity.class).getGeneric().resolve();

            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("Not found id from class of " + clazz);
            }


            // AbsMapper<I,IEntity>
            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(AbsMapper.class),
                    ClassName.get(id),
                    ClassName.get(clazz)
            );

            // public interface EntityMapper extends AbsMapper<I,IEntity>
            TypeSpec myMapper = TypeSpec.interfaceBuilder(mapperName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(parameterizedTypeName)
                    .addAnnotation(Mapper.class)
                    .addAnnotation(JavaPoets.generated(getClass()))
                    .addJavadoc(JavaPoets.Javadoc.author())
                    .addJavadoc(JavaPoets.Javadoc.version())
                    .build();

            return JavaFile.builder(mapperPackage, myMapper)
                    .skipJavaLangImports(true)
                    .indent("    ")
                    .build();
        } finally {
            logger.info("generated mapper: {}.{}", mapperPackage, mapperName);
        }
    }
}


