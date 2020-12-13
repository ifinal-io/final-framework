package org.ifinal.finalframework.data.mapping;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.ifinal.finalframework.util.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationEntity<T> extends BasicPersistentEntity<T, Property> implements Entity<T> {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationEntity.class);

    private final List<Property> properties = new ArrayList<>();

    private AnnotationEntity(final TypeInformation<T> information) {

        super(information);
    }

    public AnnotationEntity(final Class<T> entityClass) {

        this(ClassTypeInformation.from(entityClass));
        init();
    }

    private void init() {
        initProperties();
    }

    private void initProperties() {
        try {
            final Class<?> entityClass = getType();
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
            Arrays.stream(beanInfo.getPropertyDescriptors())
                .filter(it -> !"class".equals(it.getName()))
                .map(it -> buildProperty(entityClass, it))
                .forEach(it -> {
                    addPersistentProperty(it);
                    properties.add(it);
                });

            this.properties.sort(Comparator.comparing(Property::getOrder));

        } catch (IntrospectionException e) {
            logger.error("", e);
        }
    }

    private Property buildProperty(final Class<?> entityClass, final PropertyDescriptor descriptor) {

        final Field field = Reflections.findField(entityClass, descriptor.getName());
        return field == null
            ? new AnnotationProperty(
            org.springframework.data.mapping.model.Property.of(getTypeInformation(), descriptor), this,
            SimpleTypeHolder.DEFAULT)
            : new AnnotationProperty(
                org.springframework.data.mapping.model.Property.of(getTypeInformation(), field, descriptor), this,
                SimpleTypeHolder.DEFAULT);
    }

    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }

}
