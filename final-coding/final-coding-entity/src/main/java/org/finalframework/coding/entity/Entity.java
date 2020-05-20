package org.finalframework.coding.entity;

import org.finalframework.core.Streamable;
import org.finalframework.data.annotation.enums.PrimaryKeyType;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 09:55
 * @since 1.0
 */
public interface Entity extends Streamable<Property>, Iterable<Property> {


    /**
     * return the package of entity
     *
     * @see javax.lang.model.util.Elements#getPackageOf(Element)
     */
    String getPackage();

    /**
     * return the name of entity
     */
    String getName();

    /**
     * @return
     * @see org.finalframework.data.annotation.Schema
     */
    String getSchema();

    String getTable();

    /**
     * return the simpleName of entity
     */
    String getSimpleName();

    TypeElement getElement();

    /**
     * return the raw type of entity
     *
     * @see TypeElement#toString()
     */
    String getRawType();

    /**
     * return the type of entity
     *
     * @see javax.lang.model.util.Types#erasure(TypeMirror)
     */
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

    Property getVersionProperty();

    default PrimaryKeyType getPrimaryKeyType() {
        return getIdProperty() == null ? PrimaryKeyType.AUTO_INC : getIdProperty().getPrimaryKeyType();
    }

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    Collection<TypeElement> getViews();

}
