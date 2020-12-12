package org.ifinal.finalframework.auto.query.processor;

import org.ifinal.finalframework.auto.data.Entity;
import org.ifinal.finalframework.auto.data.EntityFactory;
import org.ifinal.finalframework.auto.data.Property;
import org.ifinal.finalframework.auto.query.Utils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class QEntityFactory {

    private static final String ENTITY_PREFIX = "Q";

    private QEntityFactory() {
    }

    public static QEntity create(final ProcessingEnvironment processingEnv, final String packageName, final Entity entity) {

        final String entityName = entity.getSimpleName();
        final QEntity.Builder builder = new QEntity.Builder(entity);
        builder.packageName(packageName)
                .name(ENTITY_PREFIX + entityName);
        entity.stream()
                .filter(property -> !property.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        TypeElement multiElement = property.getJavaTypeElement();
                        Entity multiEntity = EntityFactory.create(processingEnv, multiElement);
                        final List<String> properties = property.referenceProperties();
                        properties.stream()
                                .map(multiEntity::getRequiredProperty)
                                .map(multiProperty -> buildProperty(property, multiProperty))
                                .forEach(builder::addProperty);
                    } else {
                        builder.addProperty(buildProperty(null, property));
                    }

                });

        return builder.build();
    }

    private static QProperty buildProperty(final @Nullable Property referenceProperty, final @NonNull Property property) {

        if (referenceProperty == null) {
            return QProperty.builder(property.getName(), Utils.formatPropertyName(null, property))
                    .element(property.getElement())
                    .idProperty(property.isIdProperty())
                    .build();
        } else {
            final String path = referenceProperty.getName() + "." + property.getName();
            final String name = Utils.formatPropertyName(referenceProperty, property);
            return QProperty.builder(path, name)
                    .element(property.getElement())
                    .idProperty(false)
                    .build();
        }

    }

}

