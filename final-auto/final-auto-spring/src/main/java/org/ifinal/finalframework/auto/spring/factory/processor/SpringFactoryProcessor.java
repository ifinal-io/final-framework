package org.ifinal.finalframework.auto.spring.factory.processor;


import org.ifinal.finalframework.auto.model.AnnotationMirrors;
import org.ifinal.finalframework.auto.model.AnnotationValues;
import org.ifinal.finalframework.auto.service.annotation.AutoProcessor;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringFactories;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Parse the spring factory element which annotated by {@link Component} or {@link EnableAutoConfiguration}.
 *
 * @author likly
 * @version 1.0.0
 * @see ApplicationListener
 * @see ApplicationContextInitializer
 * @see EnableAutoConfiguration
 * @since 1.0.0
 */
@AutoProcessor
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SpringFactoryProcessor extends AbstractProcessor {

    /**
     * @see SpringFactory#value()
     */
    private static final String SPRING_FACTORY_VALUE = "value";
    /**
     * @see SpringFactory#extend()
     */
    private static final String SPRING_FACTORY_EXTEND = "extend";

    private static final List<Class<?>> buildInFactories = new ArrayList<>(8);
    private static final List<Class<? extends Annotation>> BUILD_IN_ANNOTATIONS = new ArrayList<>(8);

    static {
        BUILD_IN_ANNOTATIONS.add(EnableAutoConfiguration.class);

        buildInFactories.add(ApplicationListener.class);
        buildInFactories.add(ApplicationContextInitializer.class);
    }

    private final SpringFactoryResource springFactoryResource = new SpringFactoryResource();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            generateFactoryFile();
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
                            default:
                                break;
                        }
                    });
        }


        return false;
    }

    private void generateFactoryFile() {
        Filer filer = processingEnv.getFiler();
        try {
            // would like to be able to print the full path
            // before we attempt to get the resource in case the behavior
            // of filer.getResource does change to match the spec, but there's
            // no good way to resolve CLASS_OUTPUT without first getting a resource.
            FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
                    SpringFactoryResource.RESOURCE_FILE);
            InputStreamResource resource = new InputStreamResource(existingFile.openInputStream());
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<?, ?> entry : properties.entrySet()) {
                String factoryClassName = ((String) entry.getKey()).trim();
                for (String factoryName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                    springFactoryResource.addSpringFactory(factoryClassName, factoryName.trim());
                }
            }

            info("Looking for existing resource file at " + existingFile.toUri());
        } catch (IOException e) {
            // According to the javadoc, Filer.getResource throws an exception
            // if the file doesn't already exist.  In practice this doesn't
            // appear to be the case.  Filer.getResource will happily return a
            // FileObject that refers to a non-existent file but will throw
            // IOException if you try to open an input stream for it.
            info("Resource file did not already exist.");
        }

        if (springFactoryResource.getSpringFactories().isEmpty()) {
            return;
        }

        try {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", SpringFactoryResource.RESOURCE_FILE);
            springFactoryResource.writeFactoryFile(fileObject.openOutputStream());
            info("Create spring.factories :" + springFactoryResource);
        } catch (Exception e) {
            error("Create spring.factories error :" + springFactoryResource + ",\n" + e.getMessage());
        }
    }


    private void processClassTypeElement(TypeElement element) {
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                // 直接在类上声明的 SpringFactory 注解
                final Map<String, AnnotationValue> annotationValues = AnnotationMirrors.getAnnotationValues(annotationMirror);
                DeclaredType value = (DeclaredType) annotationValues.get(SPRING_FACTORY_VALUE).getValue();
                springFactoryResource.addSpringFactory((TypeElement) value.asElement(), element);
                AnnotationValue expandValue = annotationValues.get(SPRING_FACTORY_VALUE);
                if (expandValue != null && Boolean.TRUE.equals(expandValue.getValue())) {
                    this.addSpringFactory(((TypeElement) value.asElement()).getQualifiedName().toString());
                }
            } else {
                // 查看注解上是否有 SpringFactory 注解
                List<? extends AnnotationMirror> mirrors = annotationMirror.getAnnotationType().asElement().getAnnotationMirrors();
                for (AnnotationMirror mirror : mirrors) {
                    if (SpringFactory.class.getCanonicalName().equals(mirror.getAnnotationType().toString())) {
                        Map<String, AnnotationValue> annotationValues = AnnotationMirrors.getAnnotationValues(mirror);
                        final TypeElement springFactory = AnnotationValues.getClass(annotationValues.get(SPRING_FACTORY_VALUE));
                        final boolean extend = annotationValues.containsKey(SPRING_FACTORY_EXTEND) && AnnotationValues.getBoolean(annotationValues.get(SPRING_FACTORY_EXTEND));
                        springFactoryResource.addSpringFactory(springFactory, element);
                        if (Boolean.TRUE.equals(extend)) {
                            springFactoryResource.addSpringFactory(SpringFactory.class.getCanonicalName(), springFactory.getQualifiedName().toString());
                        }

                    }
                }
            }
        }
    }

    private void processAnnotationTypeElement(TypeElement element) {
        SpringFactory springFactory = element.getAnnotation(SpringFactory.class);
        if (springFactory != null && springFactory.extend()) {
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                    final Map<String, AnnotationValue> annotationValues = AnnotationMirrors.getAnnotationValues(annotationMirror);
                    DeclaredType value = (DeclaredType) annotationValues.get(SPRING_FACTORY_VALUE).getValue();
                    this.addSpringFactory(((TypeElement) value.asElement()).getQualifiedName().toString());
                }
            }
        }
    }

    private void processPackageElement(PackageElement element, RoundEnvironment roundEnv) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                processPackageSpringFactory(element, annotationMirror, roundEnv);
            } else if (annotationMirror.getAnnotationType().toString().equals(SpringFactories.class.getCanonicalName())) {
                // 在包元素上声明了多个 SpringFactory 注解
                AnnotationValue annotationValue = AnnotationMirrors.getAnnotationValues(annotationMirror).get(SPRING_FACTORY_VALUE);
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
                    } else if (mirror.getAnnotationType().toString().equals(SpringFactories.class.getCanonicalName())) {
                        /*
                         * 在注解元素{@link AnnotationMirror}上声明了多个 {@link SpringFactory}注释
                         */
                        AnnotationValue annotationValue = AnnotationMirrors.getAnnotationValues(mirror).get(SPRING_FACTORY_VALUE);
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
        final Map<String, AnnotationValue> annotationValues = AnnotationMirrors.getAnnotationValues(springFactory);
        DeclaredType value = (DeclaredType) annotationValues.get(SPRING_FACTORY_VALUE).getValue();
        AnnotationValue expandValue = annotationValues.get(SPRING_FACTORY_EXTEND);
        if (expandValue != null && Boolean.TRUE.equals(expandValue.getValue())) {
            this.addSpringFactory(((TypeElement) value.asElement()).getQualifiedName().toString());
        }
        TypeElement annotation = (TypeElement) value.asElement();
        roundEnv.getElementsAnnotatedWith(annotation)
                .stream()
                .filter(it -> processingEnv.getElementUtils().getPackageOf(it).getQualifiedName().toString().startsWith(packageElement.getQualifiedName().toString()))
                .forEach(item -> springFactoryResource.addSpringFactory(annotation, (TypeElement) item));
    }

    private void addSpringFactory(String instance) {
        addSpringFactory(SpringFactory.class.getCanonicalName(), instance);
    }

    private void addSpringFactory(String factory, String instance) {
        springFactoryResource.addSpringFactory(factory, instance);
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }


}

