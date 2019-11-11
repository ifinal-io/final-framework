package org.finalframework.coding.spring.factory;


import com.google.auto.service.AutoService;
import org.finalframework.coding.spring.factory.annotation.SpringFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    /**
     * @see SpringFactory
     */
    private TypeElement springFactoryTypeElement;
    private Map<String, ExecutableElement> springFactoryExecutableElements;

    private TypeElement springFactoryListTypeElement;
    private Map<String, ExecutableElement> springFactoryListExecutableElements;
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

    private void processClassTypeElement(TypeElement element) {
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
                        DeclaredType value = (DeclaredType) mirror.getElementValues().get(springFactoryValue).getValue();
                        springFactories.addSpringFactory((TypeElement) value.asElement(), element);
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
                processPackageSpringFactory(annotationMirror, roundEnv);
            } else if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.List.class.getCanonicalName())) {
                // 在包元素上声明了多个 SpringFactory 注解
                AnnotationValue annotationValue = annotationMirror.getElementValues().get(this.springFactoryListExecutableElements.get("value"));
                List<AnnotationMirror> springFactories = (List<AnnotationMirror>) annotationValue.getValue();
                for (AnnotationMirror springFactory : springFactories) {
                    processPackageSpringFactory(springFactory, roundEnv);
                }

            } else {
                // 查看注解上是否有 SpringFactory 注解
                List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType().asElement().getAnnotationMirrors();
                for (AnnotationMirror mirror : mirrors) {
                    if (mirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                        processPackageSpringFactory(mirror, roundEnv);
                    }
                }
            }
        }
    }

    private void processPackageSpringFactory(AnnotationMirror springFactory, RoundEnvironment roundEnv) {
        // 直接在类上声明的 SpringFactory 注解
        Map<? extends ExecutableElement, ? extends AnnotationValue> springFactoryElementValues = springFactory.getElementValues();
        DeclaredType value = (DeclaredType) springFactoryElementValues.get(springFactoryValue).getValue();
        AnnotationValue expandValue = springFactoryElementValues.get(springFactoryExpand);
        if (expandValue != null && Boolean.TRUE.equals(expandValue.getValue())) {
            springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
        }
        TypeElement annotation = (TypeElement) value.asElement();
        for (Element item : roundEnv.getElementsAnnotatedWith(annotation)) {
            springFactories.addSpringFactory(annotation, (TypeElement) item);
        }
    }

}

