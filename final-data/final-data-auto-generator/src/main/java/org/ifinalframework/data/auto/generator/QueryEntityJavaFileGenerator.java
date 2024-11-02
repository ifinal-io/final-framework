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
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.auto.annotation.AutoQuery;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.query.AbsQEntity;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QEntityFactory;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.javapoets.JavaPoets;

import javax.lang.model.element.Modifier;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * QueryEntityJavaFileGenerator.
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
public class QueryEntityJavaFileGenerator implements JavaFileGenerator<AutoQuery> {

    private final QEntityFactory entityFactory = DefaultQEntityFactory.INSTANCE;


    @Override
    public String getName(AutoQuery ann, Class<?> clazz) {
        return String.join(".", DomainNameHelper.queryEntityPackage(clazz), DomainNameHelper.queryEntityName(clazz));
    }

    @NonNull
    @Override
    public JavaFile generate(@NonNull AutoQuery ann, @NonNull Class<?> clazz) {

        final String packageName = DomainNameHelper.queryEntityPackage(clazz);
        final String entityName = DomainNameHelper.queryEntityName(clazz);

        final Class<?> id = ResolvableType.forClass(clazz).as(IEntity.class).getGeneric().resolve();

        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("not found id for class of " + clazz);
        }

        // AbsQEntity<I,IEntity>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(AbsQEntity.class),
                ClassName.get(id),
                ClassName.get(clazz)
        );

        // super(Entity.class)
        MethodSpec defaultConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super($L.class)", clazz.getSimpleName())
                .build();

        // super(Entity.class,table)
        MethodSpec tableConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                // final String table
                .addParameter(
                        ParameterSpec.builder(ClassName.get(String.class), "table").addModifiers(Modifier.FINAL).build())
                // super(Entity.class, table)
                .addStatement("super($L.class, table)", clazz.getSimpleName())
                .build();


        FieldSpec entityField = FieldSpec.builder(
                        ClassName.get(packageName, entityName),
                        clazz.getSimpleName(),
                        Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
                ).initializer("new $L()", entityName)
                .build();

        QEntity<?, ?> entity = entityFactory.create(clazz);

        List<FieldSpec> fieldSpecs = entity.stream()
                .map(property -> {
                    logger.info("property: name={},type={}", property.getName(), property.getGenericType());
                    return FieldSpec.builder(
                                    ParameterizedTypeName.get(
                                            ClassName.get(QProperty.class), TypeName.get(property.getGenericType())), property.getName())
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            // Entity.getRequiredProperty('path')
                            .initializer("$L.getRequiredProperty($S)", clazz.getSimpleName(), property.getPath())
                            .build();
                })
                .collect(Collectors.toList());

        TypeSpec queryEntity = TypeSpec.classBuilder(entityName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(parameterizedTypeName)
                .addMethod(defaultConstructor).addMethod(tableConstructor)
                .addAnnotation(JavaPoets.generated(getClass()))
                .addJavadoc(JavaPoets.Javadoc.author())
                .addJavadoc(JavaPoets.Javadoc.version())
                .addField(entityField)
                .addFields(fieldSpecs)
                .build();

        return JavaFile.builder(packageName, queryEntity)
                .skipJavaLangImports(true)
                .indent("    ")
                .build();
    }
}


