package org.finalframework.coding.entity;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.entity.IEntity;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashSet;
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
public class EntitiesGeneratorProcessor extends AbstractProcessor {
    private Coder coder = Coder.getDefaultCoder();
    private Elements elementUtils;
    private Types typeUtils;

    /**
     * @see IEntity
     */
    private TypeElement entityTypeElement;

    private Set<TypeElement> entities = new HashSet<>(128);

    private EntitiesHelper entitiesHelper;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        Configuration.getInstance().load(processingEnv);
        NameConverterRegistry.getInstance().reload();
        this.entitiesHelper = new EntitiesHelper(processingEnv);
        this.entityTypeElement = elementUtils.getTypeElement(IEntity.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            coding(Entities.builder().addEntities(entities).build());
        } else {
            roundEnv.getRootElements()
                    .stream()
                    .filter(it -> it.getKind() == ElementKind.CLASS)
                    .map(it -> ((TypeElement) it))
                    .filter(this::isEntity)
                    .forEach(entities::add);
        }
        return false;
    }

    private void coding(Entities entities) {
        entitiesHelper.generate(entities);
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
