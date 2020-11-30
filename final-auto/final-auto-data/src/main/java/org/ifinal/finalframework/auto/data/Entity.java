package org.ifinal.finalframework.auto.data;


import org.ifinal.finalframework.util.stream.Streamable;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Entity extends Streamable<Property>, Iterable<Property> {

    String getPackage();

    String getName();

    String getSchema();

    String getTable();

    String getSimpleName();

    TypeElement getElement();

    String getRawType();

    String getType();

    List<Property> getProperties();

    Property getProperty(String name);

    default Property getRequiredProperty(String name) {

        Property property = getProperty(name);

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required property of %s not found for %s!", name, getType()));
    }

    Property getIdProperty();

    default Property getRequiredIdProperty() {

        Property property = getIdProperty();

        if (property != null) {
            return property;
        }

        throw new IllegalStateException(String.format("Required identifier property not found for %s!", getType()));
    }


    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

}
