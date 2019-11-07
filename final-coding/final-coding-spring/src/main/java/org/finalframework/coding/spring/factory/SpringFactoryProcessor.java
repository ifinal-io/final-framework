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
    private TypeElement springFactoryTypeElement;
    private Map<String, ExecutableElement> springFactoryExecutableElements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.springFactoryTypeElement = processingEnv.getElementUtils().getTypeElement(SpringFactory.class.getCanonicalName());
        this.springFactoriesGenerator = new SpringFactoriesGenerator(processingEnv);
        this.springFactoryExecutableElements = this.springFactoryTypeElement.getEnclosedElements()
                .stream()
                .map(item -> ((ExecutableElement) item))
                .collect(Collectors.toMap(item -> item.getSimpleName().toString(), Function.identity()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ExecutableElement springFactoryValue = springFactoryExecutableElements.get("value");
        ExecutableElement springFactoryExpand = springFactoryExecutableElements.get("expand");
        if (roundEnv.processingOver()) {
            springFactoriesGenerator.generate(springFactories);
        } else {

            roundEnv.getRootElements()
                    .forEach(element -> {
                        switch (element.getKind()) {
                            case CLASS:
                                List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                                for (AnnotationMirror annotationMirror : annotationMirrors) {
                                    if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                                        Map<? extends ExecutableElement, ? extends AnnotationValue> annotationElementValues = annotationMirror.getElementValues();
                                        DeclaredType value = (DeclaredType) annotationElementValues.get(springFactoryValue).getValue();
                                        springFactories.addSpringFactory((TypeElement) value.asElement(), (TypeElement) element);
                                        AnnotationValue expandValue = annotationElementValues.get(springFactoryExpand);
                                        if(expandValue != null && Boolean.TRUE.equals(expandValue.getValue())){
                                            springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
                                        }

                                    } else {
                                        TypeElement typeElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
                                        List<? extends AnnotationMirror> mirrors = typeElement.getAnnotationMirrors();
                                        for (AnnotationMirror mirror : mirrors) {
                                            if (mirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
                                                DeclaredType value = (DeclaredType) mirror.getElementValues().get(springFactoryValue).getValue();
                                                springFactories.addSpringFactory((TypeElement) value.asElement(), (TypeElement) element);
                                            }
                                        }
                                    }
                                }
                                break;
                            case ANNOTATION_TYPE:
                                SpringFactory springFactory = ((Element) element).getAnnotation(SpringFactory.class);
                                if(springFactory != null && springFactory.expand()) {
                                    for (AnnotationMirror annotationMirror : ((Element) element).getAnnotationMirrors()) {
                                        if (annotationMirror.getAnnotationType().toString().equals(SpringFactory.class.getCanonicalName())) {
//                                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, annotationMirror.toString());

                                            DeclaredType value = (DeclaredType) annotationMirror.getElementValues().get(springFactoryValue).getValue();
                                            springFactories.addSpringFactory(springFactoryTypeElement, (TypeElement) value.asElement());
                                        }
                                    }
                                }
                                break;

                        }

                    });
        }


        return false;
    }

}

