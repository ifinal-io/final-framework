package org.finalframework.data.coding.entity;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.data.annotation.NonColumn;
import org.finalframework.data.annotation.enums.ReferenceMode;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    private final Coder coder = Coder.getDefaultCoder();
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(org.finalframework.data.annotation.Entity.class.getName());
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
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(org.finalframework.data.annotation.Entity.class));
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements
                .stream()
                .map(it -> EntityFactory.create(processingEnv, (TypeElement) it))
                .forEach(it -> {
                    final String packageName = it.getPackage();
                    final String entityName = it.getSimpleName();
                    final QEntity.Builder builder = new QEntity.Builder(it);
                    builder.packageName(packageName)
                            .name("Q" + entityName);
                    it.stream()
                            .filter(property -> !property.hasAnnotation(NonColumn.class))
                            .forEach(property -> {
                                if (property.isReference()) {
                                    TypeElement multiElement = processingEnv.getElementUtils().getTypeElement(property.getType());
                                    Entity<Property> multiEntity = EntityFactory.create(processingEnv, multiElement);
                                    @SuppressWarnings("unchecked") final List<String> properties = property.referenceProperties();
                                    properties.stream()
                                            .map(multiEntity::getRequiredProperty)
                                            .map(multiProperty -> {
                                                final String path = property.getName() + "." + multiProperty.getName();
                                                final String name = multiProperty.isIdProperty() ?
                                                        property.referenceMode() == ReferenceMode.SIMPLE ?
                                                                property.getName() : property.getName() + multiProperty.getName().substring(0, 1).toUpperCase() + multiProperty.getName().substring(1)
                                                        : property.getName() + multiProperty.getName().substring(0, 1).toUpperCase() + multiProperty.getName().substring(1);
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
