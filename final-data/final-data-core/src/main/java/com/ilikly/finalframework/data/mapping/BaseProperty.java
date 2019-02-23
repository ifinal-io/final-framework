package com.ilikly.finalframework.data.mapping;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.annotation.*;
import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.annotation.enums.PrimaryKeyType;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;


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

    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public BaseProperty(org.springframework.data.mapping.model.Property property, Entity owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
        logger.info("==> init property holder: entity={},property={},transient={}", getOwner().getType().getSimpleName(), getName(), isTransient());
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


        } finally {

            if (isIdProperty()) {
                unique = true;
                nonnull = true;
                updatable = false;

                if (isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey primaryKey = findAnnotation(PrimaryKey.class);
                    this.column = primaryKey.name();
                    this.insertable = primaryKey.type() != PrimaryKeyType.AUTO_INC;
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

    private void initMultiColumn(MultiColumn multiColumn) {
        this.unique = multiColumn.unique();
        this.nonnull = multiColumn.nonnull();
        this.insertable = multiColumn.insertable();
        this.updatable = multiColumn.updatable();
        this.column = multiColumn.name();
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
    protected Association<Property> createAssociation() {
        return new Association<>(this, null);
    }

}
