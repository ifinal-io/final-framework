package org.finalframework.auto.query.processor;

import org.finalframework.annotation.IEntity;
import org.finalframework.auto.coding.Coder;
import org.finalframework.auto.data.EntityFactory;
import org.finalframework.auto.query.annotation.AutoQuery;
import org.finalframework.auto.service.annotation.AutoProcessor;
import org.finalframework.io.support.ServicesLoader;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * QEntity 代码生成处理器
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@SuppressWarnings("unused")
@AutoProcessor
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AutoQueryGeneratorProcessor extends AbstractProcessor {

    private static final String DEFAULT_ENTITY_PATH = "entity";
    private static final String DEFAULT_QUERY_PATH = "dao.query";

    private final Coder coder = Coder.getDefaultCoder();

    private Elements elementUtils;
    private Types typeUtils;

    /**
     * @see IEntity
     */
    private TypeElement entityTypeElement;

    /**
     * Lombok 注解处理器
     */
    private Processor lombokProcessor;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.entityTypeElement = elementUtils.getTypeElement(IEntity.class.getCanonicalName());
        initLombokProcessor();
    }

    private void initLombokProcessor() {
        try {
            lombokProcessor = (Processor) Class.forName("lombok.core.AnnotationProcessor").newInstance();
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            ServicesLoader.load(IEntity.class, getClass().getClassLoader())
                    .stream()
                    .map(entity -> processingEnv.getElementUtils().getTypeElement(entity))
                    .forEach(entity -> this.generate(entity, DEFAULT_ENTITY_PATH, DEFAULT_QUERY_PATH));
        } else {

            if (lombokProcessor != null) {
                lombokProcessor.process(annotations, roundEnv);
            }

            ElementFilter.packagesIn(roundEnv.getElementsAnnotatedWith(AutoQuery.class))
                    .forEach(packageElement -> {
                        final AutoQuery autoQuery = packageElement.getAnnotation(AutoQuery.class);

                        Arrays.stream(autoQuery.value())
                                .map(packageName -> ElementFilter.typesIn(processingEnv.getElementUtils().getPackageElement(packageName).getEnclosedElements()))
                                .forEach(entities -> entities.stream()
                                        .filter(this::isEntity)
                                        .forEach(entity -> this.generate(entity, autoQuery)));


                    });

        }


        return false;
    }

    private void generate(TypeElement entity, AutoQuery autoQuery) {
        generate(entity, autoQuery.entity(), autoQuery.query());
    }

    private void generate(TypeElement entity, String entityPath, String queryPath) {
        final String packageName = processingEnv.getElementUtils().getPackageOf(entity).getQualifiedName().toString()
                .replace("." + Optional.ofNullable(entityPath).orElse(DEFAULT_ENTITY_PATH), "." + Optional.ofNullable(queryPath).orElse(DEFAULT_QUERY_PATH));
        generator(QEntityFactory.create(processingEnv, packageName, EntityFactory.create(processingEnv, entity)));
    }


    private void generator(QEntity entity) {
        try {
            String name = entity.getName();
            info("try to generator entity of " + name);
            TypeElement mapperElement = processingEnv.getElementUtils().getTypeElement(name);
            if (mapperElement == null) {
                coder.coding(entity, processingEnv.getFiler().createSourceFile(name).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isEntity(TypeElement typeElement) {
        return isSubtype(typeElement, entityTypeElement);

    }

    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


    private void info(String msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }


}
