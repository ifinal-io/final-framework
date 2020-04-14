package org.finalframework.data.mapping;

import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.Lazy;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:03
 * @since 1.0
 */
public class AnnotationProperty extends AnnotationBasedPersistentProperty<Property> implements Property {

    private final Lazy<Boolean> isTransient = Lazy.of(isAnnotationPresent(Transient.class) || super.isTransient());
    private final Lazy<Boolean> isDefault = Lazy.of(!isTransient() && isAnnotationPresent(Default.class));
    private final Lazy<Boolean> isFinal = Lazy.of(!isTransient() && isAnnotationPresent(Final.class));
    private final Lazy<Boolean> isVirtual = Lazy.of(!isTransient() && isAnnotationPresent(Virtual.class));
    private final Lazy<Boolean> isSharding = Lazy.of(!isTransient() && isAnnotationPresent(Sharding.class));
    private final Lazy<Boolean> isReadonly = Lazy.of(!isTransient() && isAnnotationPresent(ReadOnly.class));
    private final Lazy<Boolean> isWriteOnly = Lazy.of(!isTransient() && isAnnotationPresent(WriteOnly.class));


    private final Lazy<String> column = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Assert.isBlank(annotation.name())) return getName();
        return annotation.name();
    });


    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public AnnotationProperty(org.springframework.data.mapping.model.Property property, org.finalframework.data.mapping.Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }


    @Override
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }

    @Override
    public boolean isTransient() {
        return isTransient.get();
    }

    @Override
    public String getColumn() {
        return column.get();
    }

    @Override
    public boolean isDefault() {
        return isDefault.get();
    }

    @Override
    public boolean isFinal() {
        return isFinal.get();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public boolean isSharding() {
        return isSharding.get();
    }

    @Override
    public boolean isReadOnly() {
        return isReadonly.get();
    }

    @Override
    public boolean isWriteOnly() {
        return isWriteOnly.get();
    }
}
