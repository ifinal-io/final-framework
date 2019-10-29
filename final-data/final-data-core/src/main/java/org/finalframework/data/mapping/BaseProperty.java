package org.finalframework.data.mapping;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:03
 * @since 1.0
 */
public class BaseProperty extends AnnotationBasedPersistentProperty<Property> implements Property {

    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public BaseProperty(org.springframework.data.mapping.model.Property property, org.finalframework.data.mapping.Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }

    @Override
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }

}
