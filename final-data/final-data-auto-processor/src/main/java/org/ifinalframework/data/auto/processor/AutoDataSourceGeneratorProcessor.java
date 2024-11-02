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

import org.ifinalframework.auto.service.annotation.AutoProcessor;
import org.ifinalframework.data.auto.annotation.AutoDataSource;
import org.ifinalframework.data.auto.generator.AutoDataSourceGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AutoDataSourceGeneratorProcessor.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoDataSourceGeneratorProcessor extends AbstractProcessor {

    private AutoDataSourceGenerator autoDataSourceGenerator;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        autoDataSourceGenerator = new AutoDataSourceGenerator(processingEnv);
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(AutoDataSource.class))
                .forEach(it -> {
                    AutoDataSource annotation = it.getAnnotation(AutoDataSource.class);
                    autoDataSourceGenerator.generate(annotation, it);
                });

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(
                        AutoDataSource.class
                ).map(Class::getName)
                .collect(Collectors.toSet());
    }

}
