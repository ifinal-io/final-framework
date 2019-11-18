package org.finalframework.coding.query;

import com.google.auto.service.AutoService;
import org.finalframework.coding.entity.Entities;
import org.finalframework.coding.entity.EntitiesHelper;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.entity.IEntity;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
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
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class QEntityGeneratorProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;

    private QEntityGenerator generator;

    /**
     * @see IEntity
     */
    private TypeElement entityTypeElement;

    /**
     * Lombok 注解处理器
     */
    private Processor lombokProcessor;

    private boolean entitiesProcessed = false;
    private EntitiesHelper entitiesHelper;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.entityTypeElement = elementUtils.getTypeElement(IEntity.class.getCanonicalName());
        initLombokProcessor();
        Configuration.getInstance().load(processingEnv);
        this.entitiesHelper = new EntitiesHelper(processingEnv);
        generator = new QEntityGenerator(processingEnv);
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
        if (lombokProcessor != null) {
            lombokProcessor.process(annotations, roundEnv);
        }
        processEntities();

        roundEnv.getRootElements()
                .stream()
                .filter(it -> it.getKind() == ElementKind.CLASS)
                .map(it -> ((TypeElement) it))
                .filter(this::isEntity)
                .forEach(generator::generate);


        return false;
    }

    private void processEntities() {

        if (entitiesProcessed) return;
        Entities entities = entitiesHelper.parse();

        for (TypeElement entity : entities) {
            generator.generate(entity);
        }

        entitiesProcessed = true;
    }

    private boolean isEntity(TypeElement typeElement) {

        if (isSubtype(typeElement, entityTypeElement)) {
            return true;
        }


        return false;

    }


    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


}
