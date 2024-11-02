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
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.springframework.lang.NonNull;

import org.ifinalframework.core.IDQuery;
import org.ifinalframework.data.annotation.criterion.Equal;
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.javapoets.JavaPoets;

import javax.lang.model.element.Modifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * ServiceJavaFileGenerator.
 * <pre class="code">
 * public class EntityQuery extends PageQuery{
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class QueryJavaFileGenerator implements JavaFileGenerator<AutoService> {

    private Class<?> action;
    private Class<?> supperQuery = PageQuery.class;
    private boolean idQuery = false;


    @Override
    public String getName(@NonNull AutoService ann, @NonNull Class<?> clazz) {
        return String.join(".", DomainNameHelper.domainQueryPackage(clazz), DomainNameHelper.domainQueryName(clazz, action));
    }

    @NonNull
    @Override
    public JavaFile generate(@NonNull AutoService ann, @NonNull Class<?> clazz) {

        String queryPackage = DomainNameHelper.domainQueryPackage(clazz);
        String queryName = DomainNameHelper.domainQueryName(clazz, action);

        logger.info("start generate query for entity of {}.{}", queryPackage, queryName);

        try {

            final FieldSpec id = FieldSpec.builder(Long.class, "id", Modifier.PRIVATE)
                    .addAnnotation(Equal.class)
                    .build();


            // IDQuery<Long>
            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(IDQuery.class),
                    ClassName.get(Long.class)
            );


            // public class EntityQuery extends PageQuery implements IDQuery<Long>
            final TypeSpec.Builder builder = TypeSpec.classBuilder(queryName);
            builder
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(supperQuery)
                    .addAnnotation(Setter.class)
                    .addAnnotation(Getter.class)
                    .addAnnotation(JavaPoets.generated(getClass()))
                    .addJavadoc(JavaPoets.Javadoc.author())
                    .addJavadoc(JavaPoets.Javadoc.version());

            if (idQuery) {
                builder.addSuperinterface(parameterizedTypeName)
                        .addField(id);
            }

            final TypeSpec service = builder.build();


            return JavaFile.builder(queryPackage, service).skipJavaLangImports(true).indent("    ").build();
        } finally {
            logger.info("generated query: {}.{}", queryPackage, queryName);
        }
    }
}


