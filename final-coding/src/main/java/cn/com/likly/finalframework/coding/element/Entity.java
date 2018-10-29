package cn.com.likly.finalframework.coding.element;

import cn.com.likly.finalframework.util.Streable;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 09:55
 * @since 1.0
 */
public interface Entity<P extends Property> extends Streable<P>, Iterable<P> {

    String getPackage();

    String getName();

    String getSimpleName();

    String getRawType();

    String getType();

    List<P> getProperties();

    P getProperty(String name);

    P getIdProperty();

    default P getRequiredIdProperty() {

        P property = getIdProperty();

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
