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

package org.ifinalframework.data.auto.processor;

import com.squareup.javapoet.JavaFile;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.ifinalframework.auto.service.annotation.AutoProcessor;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IView;
import org.ifinalframework.core.lang.Transient;
import org.ifinalframework.data.auto.annotation.AutoMapper;
import org.ifinalframework.data.auto.annotation.AutoQuery;
import org.ifinalframework.data.auto.annotation.AutoService;
import org.ifinalframework.data.auto.generator.JavaFileGenerator;
import org.ifinalframework.data.auto.generator.MapperJavaFileGenerator;
import org.ifinalframework.data.auto.generator.QueryEntityJavaFileGenerator;
import org.ifinalframework.data.auto.generator.QueryJavaFileGenerator;
import org.ifinalframework.data.auto.generator.ServiceImplJavaFileGenerator;
import org.ifinalframework.data.auto.generator.ServiceJavaFileGenerator;
import org.ifinalframework.data.query.PageQuery;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * AutoMapperGeneratorProcessor.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoEntityGeneratorProcessor extends AbstractProcessor {

    @SuppressWarnings("")
    private final MultiValueMap<Class<? extends Annotation>, JavaFileGenerator<? extends Annotation>> generatorMap
            = new LinkedMultiValueMap<>();

    public AutoEntityGeneratorProcessor() {
        generatorMap.add(AutoQuery.class, new QueryEntityJavaFileGenerator());
        generatorMap.add(AutoMapper.class, new MapperJavaFileGenerator());

        generatorMap.add(AutoService.class, new QueryJavaFileGenerator());
        generatorMap.add(AutoService.class, new QueryJavaFileGenerator(IView.Detail.class, PageQuery.class, true));
        generatorMap.add(AutoService.class, new QueryJavaFileGenerator(IView.Delete.class, PageQuery.class, true));
        generatorMap.add(AutoService.class, new QueryJavaFileGenerator(IView.Update.class, PageQuery.class, true));
        generatorMap.add(AutoService.class, new ServiceJavaFileGenerator());
        generatorMap.add(AutoService.class, new ServiceImplJavaFileGenerator());

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<JavaFileGenerator<Annotation>> getJavaFileGenerators(Class<? extends Annotation> ann) {
        List generators = generatorMap.get(ann);
        return (List<JavaFileGenerator<Annotation>>) generators;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        for (Class<? extends Annotation> autoAnnotation : generatorMap.keySet()) {
            logger.info("try to found packages with annotation of {}", autoAnnotation);
            List<JavaFileGenerator<Annotation>> generators = getJavaFileGenerators(autoAnnotation);
            Set<PackageElement> packageElements = ElementFilter.packagesIn(roundEnv.getElementsAnnotatedWith(autoAnnotation));
            logger.info("found packages: {}", packageElements);
            packageElements
                    .forEach(it -> {


                        String packageName = it.getQualifiedName().toString();

                        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

                        scanner.setResourceLoader(new PathMatchingResourcePatternResolver() {
                            @Nullable
                            @Override
                            public ClassLoader getClassLoader() {
                                return AutoEntityGeneratorProcessor.this.getClass().getClassLoader();
                            }
                        });
                        //                        scanner.addExcludeFilter(new AnnotationTypeFilter(Transient.class));
                        //                        scanner.addIncludeFilter(new AssignableTypeFilter(IEntity.class));
                        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> {
                            try {
                                Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                                return IEntity.class.isAssignableFrom(clazz) && !clazz.isAnnotationPresent(Transient.class);
                            } catch (ClassNotFoundException e) {
                                logger.error("", e);
                            }
                            return false;
                        });

                        Set<BeanDefinition> components = scanner.findCandidateComponents(packageName);

                        Set<String> set = components.stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toSet());

                        logger.info("found entities: {}", set);

                        Annotation ann = it.getAnnotation(autoAnnotation);

                        set.stream()
                                .map(element -> {
                                    try {
                                        return ClassUtils.forName(element, getClass().getClassLoader());
                                    } catch (ClassNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .forEachOrdered(element -> doGenerate(ann, element, generators));

                    });
            //        }
        }


        //        if (roundEnv.processingOver()) {


        return false;
    }

    private void doGenerate(Annotation ann, Class<?> clazz, List<JavaFileGenerator<Annotation>> generators) {
        generators.forEach(generator -> doGenerator(ann, clazz, generator)
        );
    }

    private void doGenerator(Annotation ann, Class<?> clazz, JavaFileGenerator<Annotation> generator) {
        try {
            String name = generator.getName(ann, clazz);
            final TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(name);

            if (mapperElement == null) {
                final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(name);
                JavaFile javaFile = generator.generate(ann, clazz);
                try (Writer writer = sourceFile.openWriter()) {
                    javaFile.writeTo(writer);
                    writer.flush();
                }
                logger.info("==> generated javaSourceFile: {}", name);
            }

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private void error(final String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
