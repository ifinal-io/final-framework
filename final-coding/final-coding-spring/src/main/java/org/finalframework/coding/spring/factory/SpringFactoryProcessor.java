/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.coding.spring.factory;


import com.google.auto.service.AutoService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.Transient;
import org.finalframework.spring.annotation.factory.SpringFactory;
import org.mockito.Mock;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:30:09
 * @since 1.0
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SpringFactoryProcessor extends AbstractProcessor {

    private final SpringFactories springFactories = new SpringFactories();
    private SpringFactoriesGenerator springFactoriesGenerator;
    private EntityFilter entityFilter;
    /**
     * @see SpringFactory
     */
    private TypeElement springFactoryTypeElement;
    private Map<String, ExecutableElement> springFactoryExecutableElements;

    private TypeElement springFactoryListTypeElement;
    private Map<String, ExecutableElement> springFactoryListExecutableElements;

    /**
     * @see Enum
     */
    private TypeElement enumTypeElement;
    /**
     * @see SpringFactory#value()
     */
    private ExecutableElement springFactoryValue;
    /**
     * @see SpringFactory#expand()
     */
    private ExecutableElement springFactoryExpand;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.springFactoriesGenerator = new SpringFactoriesGenerator(processingEnv);
        this.entityFilter = new EntityFilter(processingEnv);

        this.enumTypeElement = processingEnv.getElementUtils().getTypeElement(Enum.class.getCanonicalName());

        this.springFactoryTypeElement = processingEnv.getElementUtils().getTypeElement(SpringFactory.class.getCanonicalName());
        this.springFactoryExecutableElements = this.springFactoryTypeElement.getEnclosedElements()
                .stream()
                .filter(item -> ((Element) item).getKind() == ElementKind.METHOD)
                .map(item -> ((ExecutableElement) item))
                .collect(Collectors.toMap(item -> item.getSimpleName().toString(), Function.identity()));
        this.springFactoryValue = springFactoryExecutableElements.get("value");
        this.springFactoryExpand = springFactoryExecutableElements.get("expand");

        this.springFactoryListTypeElement = processingEnv.getElementUtils().getTypeElement(SpringFactory.List.class.getCanonicalName());
        this.springFactoryListExecutableElements = this.springFactoryListTypeElement.getEnclosedElements()
                .stream()
                .filter(item -> ((Element) item).getKind() == ElementKind.METHOD)
                .map(item -> ((ExecutableElement) item))
                .collect(Collectors.toMap(item -> item.getSimpleName().toString(), Function.identity()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            springFactoriesGenerator.generate(springFactories);
        } else {
            roundEnv.getRootElements()
                    .forEach(element -> {


                        switch (element.getKind()) {
                            case ENUM:
                                processEnumTypeElement((TypeElement) element);
                                break;
                            case CLASS:
                                processClassTypeElement((TypeElement) element);
                                break;
                            case ANNOTATION_TYPE:
                                processAnnotationTypeElement((TypeElement) element);
                                break;
                            case PACKAGE:
                                processPackageElement((PackageElement) element, roundEnv);
                                break;
                        }
                    });
        }


        return false;
    }

    private void processEnumTypeElement(TypeElement element) {
        if (!isTransient(element)) {
            springFactories.addSpringFactory(enumTypeElement, element);
        }
    }

    private void processEntityTypeElement(TypeElement element) {
        if (!isTransient(element) && entityFilter.matches(element)) {
            springFactories.addSpringFactory(IEntity.class.getCanonicalName(), element.getQualifiedName().toString());
        }
    }

    private void processClassTypeElement(TypeElement element) {
        processEntityTypeElement(element);
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                // 直接在类上声明的 SpringFactory 注解
                Map<? extends ExecutableElement, ? extends AnnotationValue> annotationElementValues = annotationMirror.getElementValues();
                DeclaredType value = (DeclaredType) annotationElementValues.get(springFactoryValue).getValue();
                springFactories.addSpringFactory((TypeElement) value.asElement(), (TypeElement) element);
                AnnotationValue expandValue = annotationElementValues.get(springFactoryExpand);
                if (expandValue != null && Boolean.TRUE.equals(expandValue.getValue())) {
                    springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
                }
            } else {
                // 查看注解上是否有 SpringFactory 注解
                List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType().asElement().getAnnotationMirrors();
                for (AnnotationMirror mirror : mirrors) {
                    if (mirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {

                        TypeElement springFactory = null;
                        Boolean expand = null;
                        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : mirror.getElementValues().entrySet()) {
                            final String methodName = entry.getKey().getSimpleName().toString();
                            if ("value".equals(methodName)) {
                                DeclaredType value = (DeclaredType) entry.getValue().getValue();
                                springFactory = (TypeElement) value.asElement();
                            } else if ("expand".equals(methodName)) {
                                expand = (Boolean) entry.getValue().getValue();

                            }

                        }
                        springFactories.addSpringFactory(springFactory, element);
                        if (Boolean.TRUE.equals(expand)) {
                            springFactories.addSpringFactory(SpringFactory.class.getCanonicalName(), springFactory.getQualifiedName().toString());
                        }

                    }
                }
            }
        }
    }

    private void processAnnotationTypeElement(TypeElement element) {
        SpringFactory springFactory = element.getAnnotation(SpringFactory.class);
        if (springFactory != null && springFactory.expand()) {
            for (AnnotationMirror annotationMirror : ((Element) element).getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                    DeclaredType value = (DeclaredType) annotationMirror.getElementValues().get(springFactoryValue).getValue();
                    springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
                }
            }
        }
    }

    private void processPackageElement(PackageElement element, RoundEnvironment roundEnv) {
        for (AnnotationMirror annotationMirror : ((Element) element).getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                processPackageSpringFactory(element, annotationMirror, roundEnv);
            } else if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.List.class.getCanonicalName())) {
                // 在包元素上声明了多个 SpringFactory 注解
                AnnotationValue annotationValue = annotationMirror.getElementValues().get(this.springFactoryListExecutableElements.get("value"));
                List<AnnotationMirror> springFactories = (List<AnnotationMirror>) annotationValue.getValue();
                for (AnnotationMirror springFactory : springFactories) {
                    processPackageSpringFactory(element, springFactory, roundEnv);
                }

            } else {
                // 查看注解上是否有 SpringFactory 注解
                List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType().asElement().getAnnotationMirrors();
                for (AnnotationMirror mirror : mirrors) {
                    if (mirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                        processPackageSpringFactory(element, mirror, roundEnv);
                    } else if (mirror.getAnnotationType().toString().equals(SpringFactory.List.class.getCanonicalName())) {
                        /**
                         * 在注解元素{@link AnnotationMirror}上声明了多个 {@link SpringFactory}注释
                         */
                        AnnotationValue annotationValue = mirror.getElementValues().get(this.springFactoryListExecutableElements.get("value"));
                        List<AnnotationMirror> springFactories = (List<AnnotationMirror>) annotationValue.getValue();
                        for (AnnotationMirror springFactory : springFactories) {
                            processPackageSpringFactory(element, springFactory, roundEnv);
                        }
                    }
                }
            }
        }
    }

    private void processPackageSpringFactory(PackageElement packageElement, AnnotationMirror springFactory, RoundEnvironment roundEnv) {
        // 直接在类上声明的 SpringFactory 注解
        Map<? extends ExecutableElement, ? extends AnnotationValue> springFactoryElementValues = springFactory.getElementValues();
        DeclaredType value = (DeclaredType) springFactoryElementValues.get(springFactoryValue).getValue();
        AnnotationValue expandValue = springFactoryElementValues.get(springFactoryExpand);
        if (expandValue != null && Boolean.TRUE.equals(expandValue.getValue())) {
            springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
        }
        TypeElement annotation = (TypeElement) value.asElement();
        roundEnv.getElementsAnnotatedWith(annotation)
                .stream()
                .filter(it -> processingEnv.getElementUtils().getPackageOf(it).getQualifiedName().toString().startsWith(packageElement.getQualifiedName().toString()))
                .forEach(item -> springFactories.addSpringFactory(annotation, (TypeElement) item));
    }

    private boolean isTransient(Element element) {
        return element.getAnnotation(Transient.class) != null;
    }

}

