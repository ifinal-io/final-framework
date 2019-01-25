package com.ilikly.finalframework.coding.plugins.mybatis;

import com.google.auto.service.AutoService;
import com.ilikly.finalframework.coding.coder.Coder;
import com.ilikly.finalframework.coding.coder.FreeMakerCoder;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Generate mapper.xml file of the mapper which annotated by {@link Mapper}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @see MapperXml
 * @since 1.0
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class MapperProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MapperProcessor.class);
    private final Coder coder = new FreeMakerCoder();
    private final Set<Element> mapperElements = new HashSet<>();
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Mapper.class.getName());
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
            generateEntityFiles(mapperElements);
        } else {
            mapperElements.addAll(roundEnv.getElementsAnnotatedWith(Mapper.class));
        }
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements.stream()
                .map(it -> ((TypeElement) it).getQualifiedName().toString())
                .filter(it -> {
                    final String resourceFile = it.replace(".", "/") + ".xml";
                    try {
                        // would like to be able to print the full path
                        // before we attempt to get the resource in case the behavior
                        // of filer.getResource does change to match the spec, but there's
                        // no good way to resolve CLASS_OUTPUT without first getting a resource.
                        FileObject existingFile = filer.getResource(StandardLocation.CLASS_PATH, "", resourceFile);

                        logger.info("========Resource file is already exist. {}", resourceFile);
                        info("Looking for existing resource file at " + existingFile.toUri());
                        return false;
                    } catch (IOException e) {
//                        logger.error("Resource file found exception.{}",resourceFile,e);
                        // According to the javadoc, Filer.getResource throws an exception
                        // if the file doesn't already exist.  In practice this doesn't
                        // appear to be the case.  Filer.getResource will happily return a
                        // FileObject that refers to a non-existent file but will throw
                        // IOException if you try to open an input stream for it.
                        info("Resource file did not already exist.");
                        logger.info("========Resource file did not already exist. {}", resourceFile);
                        return true;
                    }
                })
                .forEach(it -> {
                    try {
                        final String resourceFile = it.replace(".", "/") + ".xml";
                        FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                        coder.coding(new MapperXml(it), fileObject.openWriter());
                        info("Create mapper.xml of mapper:" + it);
                    } catch (Exception e) {
                        error("Create mapper.xml error of mapper:" + it + "," + e.getMessage());
                    }
                });
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
