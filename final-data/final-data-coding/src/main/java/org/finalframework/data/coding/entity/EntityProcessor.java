package org.finalframework.data.coding.entity;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.core.configuration.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
        Configuration.getInstance().load(processingEnv);
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

}
