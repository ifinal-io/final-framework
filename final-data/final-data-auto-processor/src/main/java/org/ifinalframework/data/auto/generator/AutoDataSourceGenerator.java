/*
 * Copyright 2020-2021 the original author or authors.
 *
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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.ifinalframework.data.auto.annotation.AutoDataSource;
import org.ifinalframework.data.mybatis.MyBatisDataSourceConfigurationSupport;
import org.ifinalframework.javapoets.JavaPoets;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.sql.DataSource;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import java.io.Writer;
import java.util.Objects;

/**
 * AutoDataSourceGenerator.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AutoDataSourceGenerator implements AutoGenerator<AutoDataSource, TypeElement> {

    private final ProcessingEnvironment processingEnv;

    public AutoDataSourceGenerator(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    @Override
    public void generate(final AutoDataSource ann, final TypeElement element) {

        try {
            String simpleName = element.getSimpleName().toString().replace("DataSource", "");
            String prefix = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            String dataSourceAutoConfiguration = simpleName + "DataSourceAutoConfiguration";
            Builder classBuilder = TypeSpec.classBuilder(dataSourceAutoConfiguration)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(MyBatisDataSourceConfigurationSupport.class));

            classBuilder.addAnnotation(Configuration.class);
            classBuilder.addAnnotation(JavaPoets.generated(AutoDataSourceGenerator.class));

            final boolean primary = Objects.nonNull(element.getAnnotation(Primary.class));

            final String dataSourceMethodName = prefix + DataSource.class.getSimpleName();
            final String sqlSessionFactoryName = prefix + SqlSessionFactory.class.getSimpleName();
            final String sqlSessionTemplateName = prefix + SqlSessionTemplate.class.getSimpleName();

            if (ann.mapperScan().length > 0) {

                AnnotationSpec.Builder mapperScanBuilder = AnnotationSpec.builder(MapperScan.class);

                AnnotationSpec.get(ann).members.get("mapperScan")
                        .forEach(it -> mapperScanBuilder.addMember("value", it));

                mapperScanBuilder.addMember("sqlSessionFactoryRef", String.format("\"%s\"", sqlSessionFactoryName));
                mapperScanBuilder.addMember("sqlSessionTemplateRef", String.format("\"%s\"", sqlSessionTemplateName));

                AnnotationSpec mapperScan = mapperScanBuilder.build();

                classBuilder.addAnnotation(mapperScan);
            }

            classBuilder.addMethod(dataSource(ann, dataSourceMethodName, primary));
            classBuilder.addMethod(sqlSessionFactory(dataSourceMethodName, sqlSessionFactoryName, primary));
            classBuilder.addMethod(sqlSessionTemplate(sqlSessionTemplateName, sqlSessionFactoryName, primary));

            TypeSpec dataSourceConfiguration = classBuilder.build();
            String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();

            final JavaFileObject sourceFile = processingEnv.getFiler()
                    .createSourceFile(String.join(".", packageName, dataSourceAutoConfiguration));

            try (Writer writer = sourceFile.openWriter()) {
                JavaFile javaFile = JavaFile.builder(packageName, dataSourceConfiguration)
                        .skipJavaLangImports(true)
                        .indent("    ")
                        .build();
                javaFile.writeTo(writer);
                writer.flush();
            }

        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
        }

    }

    private MethodSpec dataSource(AutoDataSource autoDataSource, String dataSourceName, boolean primary) {
        MethodSpec.Builder dataSourceMethodBuilder = MethodSpec.methodBuilder(dataSourceName);
        if (primary) {
            dataSourceMethodBuilder.addAnnotation(Primary.class);
        }
        dataSourceMethodBuilder
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(DataSource.class))
                .addAnnotation(Bean.class)
                .addAnnotation(
                        AnnotationSpec.builder(ConfigurationProperties.class)
                                .addMember("value", String.format("\"%s\"", autoDataSource.value()))
                                .build())
                .addStatement("return $T.create().build()", DataSourceBuilder.class)
                .build();

        return dataSourceMethodBuilder.build();
    }

    private MethodSpec sqlSessionFactory(String dataSourceName, String sqlSessionFactoryName, boolean primary) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(sqlSessionFactoryName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(

                        // @Qualifier DataSource dataSource
                        ParameterSpec.builder(ClassName.get(DataSource.class), "dataSource")
                                .addAnnotation(
                                        AnnotationSpec.builder(Qualifier.class)
                                                .addMember("value", String.format("\"%s\"", dataSourceName))
                                                .build())
                                .build()
                )
                .returns(ClassName.get(SqlSessionFactory.class))
                .addException(ClassName.get(Exception.class));

        if (primary) {
            builder.addAnnotation(Primary.class);
        }
        builder.addAnnotation(Bean.class)
                .addStatement("return sqlSessionFactory(dataSource)");

        return builder.build();
    }

    private MethodSpec sqlSessionTemplate(String sqlSessionTemplate, String sqlSessionFactory, boolean primary) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(sqlSessionTemplate)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        // @Qualifier SqlSessionFactory sqlSessionFactory
                        ParameterSpec.builder(ClassName.get(SqlSessionFactory.class), "sqlSessionFactory")
                                .addAnnotation(
                                        AnnotationSpec.builder(Qualifier.class)
                                                .addMember("value", String.format("\"%s\"", sqlSessionFactory))
                                                .build())
                                .build()
                )
                .returns(ClassName.get(SqlSessionTemplate.class));
        if (primary) {
            builder.addAnnotation(Primary.class);
        }
        builder.addAnnotation(Bean.class)
                .addStatement("return super.sqlSessionTemplate(sqlSessionFactory)");

        return builder.build();
    }

}


