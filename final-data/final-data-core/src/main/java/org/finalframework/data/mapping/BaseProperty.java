package org.finalframework.data.mapping;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:03
 * @since 1.0
 */
@Slf4j
@Getter
public class BaseProperty extends AnnotationBasedPersistentProperty<Property> implements Property {

    private String table;
    private String column;
    private PersistentType persistentType = PersistentType.AUTO;
    private boolean unique = false;
    private boolean nonnull = true;
    private boolean insertable = true;
    private boolean updatable = true;
    private boolean selectable = true;
    private boolean placeholder = true;
    private ReferenceMode referenceMode;
    private List<String> referenceProperties;
    private Map<String, String> referenceColumns;
    private Class<?>[] views;

    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public BaseProperty(org.springframework.data.mapping.model.Property property, org.finalframework.data.mapping.Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
//        logger.info("==> init property holder: entity={},property={},transient={}", getOwner().getType().getSimpleName(), getName(), isTransient());
        init();
    }

    private void init() {
        initColumn();
    }

    @SuppressWarnings("all")
    private void initColumn() {
        try {
            if (isAnnotationPresent(Column.class)) {
                initColumn(findAnnotation(Column.class));
            } else if (isAnnotationPresent(JsonColumn.class)) {
                initJsonColumn(findAnnotation(JsonColumn.class));
            } else if (isAnnotationPresent(CreatedTime.class)) {
                initCreatedTime(findAnnotation(CreatedTime.class));
            } else if (isAnnotationPresent(LastModifiedTime.class)) {
                initLastModifiedTime(findAnnotation(LastModifiedTime.class));
            } else if (isAnnotationPresent(ReferenceColumn.class)) {
                initReferenceColumn(findAnnotation(ReferenceColumn.class));
            } else if (isAnnotationPresent(MultiColumn.class)) {
                initMultiColumn(findAnnotation(MultiColumn.class));
            } else {
                insertable = true;
                updatable = true;
                selectable = true;
                unique = false;
                nonnull = false;
                placeholder = true;
            }

            final ColumnView columnView = findAnnotation(ColumnView.class);
            if (columnView != null) {
                views = columnView.value();
            }


        } finally {

            if (isIdProperty()) {
                unique = true;
                nonnull = true;
                updatable = false;
                insertable = false;
                selectable = true;

                if (isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey primaryKey = findAnnotation(PrimaryKey.class);
                    this.column = primaryKey.name();
                    this.unique = primaryKey.unique();
                    this.nonnull = primaryKey.nonnull();
                    this.insertable = primaryKey.insertable();
                    this.updatable = primaryKey.updatable();
                    this.selectable = primaryKey.selectable();

                }

            }

            if (Assert.isEmpty(this.table)) {
                this.table = ((Entity) getOwner()).getTable();
            }

            if (Assert.isEmpty(this.column)) {
                column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(getName());
            }
        }
    }

    @SuppressWarnings("all")
    private void initColumn(Column column) {
        this.persistentType = column.persistentType();
        this.unique = column.unique();
        this.nonnull = column.nonnull();
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.selectable = column.selectable();
        this.table = column.table();
        this.column = column.name();
        this.placeholder = column.placeholder();
    }

    @SuppressWarnings("all")
    private void initJsonColumn(JsonColumn column) {
        this.persistentType = column.persistentType();
        this.unique = column.unique();
        this.nonnull = column.nonnull();
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.selectable = column.selectable();
        this.table = column.table();
        this.column = column.name();
        this.placeholder = column.placeholder();
    }


    @SuppressWarnings("all")
    private void initCreatedTime(CreatedTime createdTime) {
        this.unique = createdTime.unique();
        this.nonnull = createdTime.nonnull();
        this.insertable = createdTime.insertable();
        this.updatable = createdTime.updatable();
        this.selectable = createdTime.selectable();
        this.table = createdTime.table();
        this.column = createdTime.name();
        this.placeholder = createdTime.placeholder();
    }

    @SuppressWarnings("all")
    private void initLastModifiedTime(LastModifiedTime lastModifiedTime) {
        this.unique = lastModifiedTime.unique();
        this.nonnull = lastModifiedTime.nonnull();
        this.insertable = lastModifiedTime.insertable();
        this.updatable = lastModifiedTime.updatable();
        this.selectable = lastModifiedTime.selectable();
        this.table = lastModifiedTime.table();
        this.column = lastModifiedTime.name();
        this.placeholder = lastModifiedTime.placeholder();
    }

    @SuppressWarnings("all")
    private void initReferenceColumn(ReferenceColumn referenceColumn) {
        this.unique = referenceColumn.unique();
        this.nonnull = referenceColumn.nonnull();
        this.insertable = referenceColumn.insertable();
        this.updatable = referenceColumn.updatable();
        this.selectable = referenceColumn.selectable();
        this.column = referenceColumn.name();
        initReference(referenceColumn.mode(), referenceColumn.properties(), referenceColumn.delimiter());
    }

    @SuppressWarnings("all")
    private void initMultiColumn(MultiColumn multiColumn) {
        this.unique = multiColumn.unique();
        this.nonnull = multiColumn.nonnull();
        this.insertable = multiColumn.insertable();
        this.updatable = multiColumn.updatable();
        this.selectable = multiColumn.selectable();
        this.column = multiColumn.name();
        initReference(multiColumn.mode(), multiColumn.properties(), multiColumn.delimiter());
    }

    private void initReference(ReferenceMode mode, String[] properties, String delimiter) {
        this.referenceMode = mode;
        List<String> referenceProperties = new ArrayList<>(properties.length);
        Map<String, String> referenceColumns = new HashMap<>(properties.length);
        for (String property : properties) {
            if (property.contains(delimiter)) {
                final String[] split = property.split(delimiter);
                referenceProperties.add(split[0]);
                referenceColumns.put(split[0], split[1]);
            } else {
                referenceProperties.add(property);
            }
        }
        this.referenceProperties = referenceProperties;
        this.referenceColumns = referenceColumns;

    }


    @Override
    public boolean hasView(Class<?> view) {
        if (views == null || views.length == 0) return false;

        for (Class<?> item : views) {
            if (item.isAssignableFrom(view)) return true;
        }

        return false;
    }

    @Override
    public boolean unique() {
        return unique;
    }

    @Override
    public boolean nonnull() {
        return nonnull;
    }

    @Override
    public boolean insertable() {
        return insertable;
    }

    @Override
    public boolean updatable() {
        return updatable;
    }


    @Override
    public boolean selectable() {
        return selectable;
    }

    @Override
    public boolean placeholder() {
        return placeholder;
    }

    @Override
    public boolean isReference() {
        return isAssociation();
    }

    @Override
    public ReferenceMode referenceMode() {
        return this.referenceMode;
    }

    @Override
    public List<String> referenceProperties() {
        return this.referenceProperties;
    }

    @Override
    public String referenceColumn(String property) {
        return this.referenceColumns == null ? null : this.referenceColumns.get(property);
    }

    @Override
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }

}
