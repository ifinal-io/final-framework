package org.finalframework.data.coding.entity;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.entity.IEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntityProcessor extends AbstractProcessor {
    private final Coder coder = Coder.getDefaultCoder();

    private Types typeUtils;
    private Filer filer;
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
        filer = processingEnv.getFiler();
        this.entityTypeElement = processingEnv.getElementUtils().getTypeElement(IEntity.class.getCanonicalName());
        initLombokProcessor();
        Configuration.getInstance().load(processingEnv);
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
        Set<? extends TypeElement> typeElements = roundEnv.getRootElements()
                .stream()
                .filter(it -> it.getKind() == ElementKind.CLASS && isEntity(it))
                .map(it -> ((TypeElement) it))
                .collect(Collectors.toSet());

        if (lombokProcessor != null) {
            lombokProcessor.process(annotations, roundEnv);
        }

        generateEntityFiles(typeElements);
        return false;
    }

    private void generateEntityFiles(Set<? extends TypeElement> elements) {
        elements.stream()
                .map(it -> EntityFactory.create(processingEnv, it))
                .forEach(it -> {
                    try {

                        final String packageName = it.getPackage() + ".query";
                        final String entityName = it.getSimpleName();
                        final QEntity.Builder builder = new QEntity.Builder(it);
                        builder.packageName(packageName)
                                .name("Q" + entityName);
                        it.stream()
                                .filter(property -> !property.isTransient())
                                .forEach(property -> {
                                    if (property.isReference()) {
                                        TypeElement multiElement = processingEnv.getElementUtils().getTypeElement(property.getType());
                                        Entity<Property> multiEntity = EntityFactory.create(processingEnv, multiElement);
                                        @SuppressWarnings("unchecked") final List<String> properties = property.referenceProperties();
                                        properties.stream()
                                                .map(multiEntity::getRequiredProperty)
                                                .map(multiProperty -> this.buildProperty(property, multiProperty))
                                                .forEach(builder::addProperty);
                                    } else {
                                        builder.addProperty(this.buildProperty(null, property));
                                    }

                                });

                        try {
                            QEntity entity = builder.build();
                            coder.coding(entity, filer
                                    .createSourceFile(entity.getPackage() + "." + entity.getName())
                                    .openWriter());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {

                    }
                });


    }

    private QProperty buildProperty(@Nullable Property referenceProperty, @NonNull Property property) {
        if (referenceProperty == null) {
            return QProperty.builder(property.getName(), Utils.formatPropertyName(null, property))
                    .type(property.getMetaTypeElement().getQualifiedName().toString())
                    .idProperty(property.isIdProperty())
                    .column(Utils.formatPropertyColumn(null, property))
                    .idProperty(property.isIdProperty())
                    .persistentType(property.getPersistentType())
                    .insertable(property.insertable())
                    .updatable(property.updatable())
                    .selectable(property.selectable())
                    .build();
        } else {
            final String path = referenceProperty.getName() + "." + property.getName();
            final String name = Utils.formatPropertyName(referenceProperty, property);
            return QProperty.builder(path, name)
                    .column(Utils.formatPropertyColumn(referenceProperty, property))
                    .type(property.getMetaTypeElement().getQualifiedName().toString())
                    .idProperty(false)
                    .persistentType(property.getPersistentType())
                    .insertable(referenceProperty.insertable())
                    .updatable(referenceProperty.updatable())
                    .selectable(referenceProperty.selectable())
                    .build();
        }

    }

    private boolean isEntity(Element typeElement) {
        return isSubtype(typeElement, entityTypeElement);
    }


    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


}
