package com.ilikly.finalframework.coding.plugins.entity;

import com.google.auto.service.AutoService;
import com.ilikly.finalframework.coding.coder.Coder;
import com.ilikly.finalframework.coding.coder.FreeMakerCoder;
import com.ilikly.finalframework.coding.element.EntityFactory;
import com.ilikly.finalframework.coding.element.Property;
import com.ilikly.finalframework.data.annotation.Entity;
import com.ilikly.finalframework.data.annotation.MultiColumn;
import com.ilikly.finalframework.data.annotation.NonColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EntityProcessor.class);
    private final Coder coder = new FreeMakerCoder();
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add("com.ilikly.finalframework.data.annotation.Entity");
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
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(Entity.class));
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements
                .stream()
                .map(it -> EntityFactory.create(processingEnv, (TypeElement) it))
//                .collect(Collectors.toList())
                .forEach(it -> {
                    final String packageName = it.getPackage();
                    final String entityName = it.getSimpleName();
                    final QEntity.Builder builder = new QEntity.Builder(it);
                    builder.packageName(packageName)
                            .name("Q" + entityName);
                    it.stream()
                            .filter(property -> !property.hasAnnotation(NonColumn.class))
                            .forEach(property -> {
                                if (property.hasAnnotation(MultiColumn.class)) {
                                    TypeElement multiElement = processingEnv.getElementUtils().getTypeElement(property.getType());
                                    logger.info("=====================================", multiElement.getQualifiedName().toString());
                                    com.ilikly.finalframework.coding.element.Entity<Property> multiEntity = EntityFactory.create(processingEnv, multiElement);
                                    MultiColumn multiColumn = (MultiColumn) property.getAnnotation(MultiColumn.class);
                                    Arrays.stream(multiColumn.properties())
                                            .peek(name -> logger.info("----{}",name))
                                            .map(multiEntity::getProperty)
                                            .map(multiProperty -> {
                                                logger.info("---------{}",multiProperty);
                                                logger.info("property:{},multiProperty:{}",property.getName(),multiProperty.getName());
                                                final String path = property.getName() + "." + multiProperty.getName();
                                                final String name = multiProperty.isIdProperty() ?
                                                        property.getName() : property.getName() + multiProperty.getName().substring(0, 1).toUpperCase() + multiProperty.getName().substring(1);
                                                return QProperty.builder(path, name)
                                                        .type(multiProperty.getType())
                                                        .rawType(multiProperty.getRawType())
                                                        .build();
                                            }).forEach(builder::addProperty);
                                } else {
                                    builder.addProperty(
                                            QProperty.builder(property.getName(), property.getName())
                                                    .type(property.getType())
                                                    .rawType(property.getRawType())
                                                    .idProperty(property.isIdProperty())
                                                    .build()
                                    );
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
                });


    }


}
