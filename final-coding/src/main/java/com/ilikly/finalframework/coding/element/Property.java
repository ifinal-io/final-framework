package com.ilikly.finalframework.coding.element;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-26 20:05
 * @since 1.0
 */
public interface Property<T extends Entity, P extends Property<T, P>> {

    Element getElement();

    String getName();

    String getType();

    String getComponentType();

    String getRawType();

    String getMapKeyType();

    String getMapValueType();

    boolean isIdProperty();

    boolean isCollection();

    boolean isMap();

    boolean isArray();

    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    default <A extends Annotation> boolean hasAnnotation(Class<A> annotationType) {
        return getAnnotation(annotationType) != null;
    }


}
