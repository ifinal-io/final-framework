package org.finalframework.coding.spring.processor;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.coding.spring.ApplicationEventListener;
import org.finalframework.coding.spring.AutoConfiguration;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class SpringFactoryProcessor extends AbstractProcessor {
    private final Coder coder = Coder.getDefaultCoder();
    private final Set<Element> processorElements = new HashSet<>();
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ApplicationEventListener.class.getName());
        types.add(AutoConfiguration.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateEntityFiles(processorElements);
        } else {
            processorElements.addAll(roundEnv.getElementsAnnotatedWith(ApplicationEventListener.class));
            processorElements.addAll(roundEnv.getElementsAnnotatedWith(AutoConfiguration.class));
        }
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {

        final String resourceFile = "META-INF/spring.factories";
        try {
            // would like to be able to print the full path
            // before we attempt to get the resource in case the behavior
            // of filer.getResource does change to match the spec, but there's
            // no good way to resolve CLASS_OUTPUT without first getting a resource.
            FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
                    resourceFile);
            info("Looking for existing resource file at " + existingFile.toUri());
        } catch (IOException e) {
            // According to the javadoc, Filer.getResource throws an exception
            // if the file doesn't already exist.  In practice this doesn't
            // appear to be the case.  Filer.getResource will happily return a
            // FileObject that refers to a non-existent file but will throw
            // IOException if you try to open an input stream for it.
            info("Resource file did not already exist.");
        }

        final SpringFactories.Builder builder = new SpringFactories.Builder();

        elements.stream().map(it -> (TypeElement) it)
                .forEach(element -> {
                    if (element.getAnnotation(AutoConfiguration.class) != null) {
                        builder.addAutoConfiguration(element.getQualifiedName().toString());
                    }

                    if (element.getAnnotation(ApplicationEventListener.class) != null) {
                        builder.addApplicationListener(element.getQualifiedName().toString());
                    }
                });

        SpringFactories springFactories = builder.build();
//        logger.info("Create spring.factories: {}", springFactories);
        try {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
            coder.coding(springFactories, fileObject.openWriter());
            info("Create spring.factories :" + springFactories);
        } catch (Exception e) {
//            logger.error("Create spring.factories error :", e);
            error("Create spring.factories error :" + springFactories + ",\n" + e.getMessage());
        }

    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
