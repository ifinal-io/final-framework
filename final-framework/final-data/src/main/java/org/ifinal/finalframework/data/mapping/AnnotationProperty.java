package org.ifinal.finalframework.data.mapping;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.data.*;
import org.ifinal.finalframework.data.mapping.converter.NameConverterRegistry;
import org.ifinal.finalframework.data.query.type.JsonParameterTypeHandler;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


/**
 * Simple implementation of {@link Property}
 *
 * @author likly
 * @version 1.0.0
 * @see AnnotationBasedPersistentProperty
 * @since 1.0.0
 */
public class AnnotationProperty extends AnnotationBasedPersistentProperty<Property> implements Property {

    /**
     * @see Column#value()
     * @see Column#name()
     */
    private final Lazy<String> column = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Asserts.isBlank(annotation.name())) {
            return getName();
        }
        return annotation.name();
    });

    /**
     * @see Column#writer()
     */
    private final Lazy<String> writer = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Asserts.isBlank(annotation.writer())) {
            return null;
        }
        return annotation.writer();
    });


    /**
     * @see Column#reader()
     */
    private final Lazy<String> reader = Lazy.of(() -> {
        final Column annotation = findAnnotation(Column.class);
        if (annotation == null || Asserts.isBlank(annotation.reader())) {
            return null;
        }
        return annotation.reader();
    });


    /**
     * @see Order
     */
    private final Lazy<Integer> order = Lazy.of(isAnnotationPresent(Order.class) ? getRequiredAnnotation(Order.class).value() : 0);
    /**
     * @see Default
     */
    private final Lazy<Boolean> isDefault = Lazy.of(!isTransient() && isAnnotationPresent(Default.class));
    /**
     * @see Final
     */
    private final Lazy<Boolean> isFinal = Lazy.of(!isTransient() && isAnnotationPresent(Final.class));
    /**
     * @see Virtual
     */
    private final Lazy<Boolean> isVirtual = Lazy.of(!isTransient() && isAnnotationPresent(Virtual.class));
    /**
     * @see ReadOnly
     */
    private final Lazy<Boolean> isReadonly = Lazy.of(!isTransient() && isAnnotationPresent(ReadOnly.class));
    /**
     * @see WriteOnly
     */
    private final Lazy<Boolean> isWriteOnly = Lazy.of(!isTransient() && isAnnotationPresent(WriteOnly.class));
    /**
     * @see Keyword
     */
    private final Lazy<Boolean> isKeyword = Lazy.of(!isTransient() && (isAnnotationPresent(Keyword.class) || SqlKeyWords.contains(getColumn())));
    private final Lazy<ReferenceMode> referenceMode = Lazy.of(isReference() ? getRequiredAnnotation(Reference.class).mode() : ReferenceMode.SIMPLE);
    private final Lazy<Map<String, String>> referenceColumns = Lazy.of(() -> {

        Map<String, String> map = new HashMap<>();

        if (isReference()) {
            final Reference reference = getRequiredAnnotation(Reference.class);
            for (String property : reference.properties()) {
                if (property.contains(reference.delimiter())) {
                    final String[] split = property.split(reference.delimiter());
                    map.put(split[0], split[1]);
                } else {
                    map.put(property, null);
                }
            }
        }

        return map;
    });

    @SuppressWarnings("rawtypes")
    private final Lazy<Class<? extends TypeHandler>> typeHandler = Lazy.of(() -> {
        if (isAnnotationPresent(Column.class)) {
            Class<? extends TypeHandler> handler = getRequiredAnnotation(Column.class).typeHandler();
            if (TypeHandler.class != handler) {
                return handler;
            }
        }

        if (isAnnotationPresent(Json.class)) {
            return JsonParameterTypeHandler.class;
        }

        if (isCollectionLike()) {
            return JsonParameterTypeHandler.class;
        }

        return null;

    });


    public AnnotationProperty(org.springframework.data.mapping.model.Property property, org.ifinal.finalframework.data.mapping.Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }


    @Override
    @NonNull
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }


    @Override
    public int getOrder() {
        return order.get();
    }

    @Override
    public String getColumn() {
        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column.get());
    }

    @Override
    public String getWriter() {
        return this.writer.getNullable();
    }

    @Override
    public String getReader() {
        return this.reader.getNullable();
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
    public boolean isReference() {
        return isAssociation();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public boolean isReadOnly() {
        return isReadonly.get();
    }

    @Override
    public boolean isWriteOnly() {
        return isWriteOnly.get();
    }

    @Override
    public boolean isKeyword() {
        return isKeyword.get();
    }

    @Override
    public ReferenceMode getReferenceMode() {
        return referenceMode.get();
    }

    @Override
    public Set<String> getReferenceProperties() {
        return referenceColumns.get().keySet();
    }

    @Override
    public String getReferenceColumn(Property property) {
        return Optional.ofNullable(referenceColumns.get().get(property.getName())).orElse(property.getColumn());
    }


    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends TypeHandler> getTypeHandler() {
        return typeHandler.getNullable();
    }
}
