package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.annotation.Column;
import cn.com.likly.finalframework.data.annotation.CreatedTime;
import cn.com.likly.finalframework.data.annotation.LastModifiedTime;
import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.annotation.enums.PersistentType;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mapping.Property;
import cn.com.likly.finalframework.util.Assert;
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
public class BaseProperty extends AnnotationBasedPersistentProperty<Property> implements QProperty {

    private String table;
    private String column;
    private PersistentType persistentType = PersistentType.AUTO;
    private boolean unique = false;
    private boolean nonnull = true;
    private boolean insertable = true;
    private boolean updatable = true;


    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
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
            } else if (isAnnotationPresent(CreatedTime.class)) {
                initCreatedTime(findAnnotation(CreatedTime.class));
            } else if (isAnnotationPresent(LastModifiedTime.class)) {
                initLastModifiedTime(findAnnotation(LastModifiedTime.class));
            }


        } finally {


            if (isIdProperty()) {
                unique = true;
                nonnull = false;
                updatable = false;

                if (isAnnotationPresent(PrimaryKey.class)) {
                    this.column = findAnnotation(PrimaryKey.class).name();
                }

            }


            if (Assert.isEmpty(this.table)) {
                this.table = ((Entity) getOwner()).getTable();
            }

            if (Assert.isEmpty(this.column)) {
                this.column = getName();
            }


        }


    }

    @SuppressWarnings("all")
    private void initColumn(Column column) {
        this.unique = column.unique();
        this.nonnull = column.nonnull();
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.table = column.table();
        this.column = column.name();
    }


    @SuppressWarnings("all")
    private void initCreatedTime(CreatedTime createdTime) {
        this.unique = createdTime.unique();
        this.nonnull = createdTime.nonnull();
        this.insertable = createdTime.insertable();
        this.updatable = createdTime.updatable();
        this.table = createdTime.table();
        this.column = createdTime.name();
    }

    @SuppressWarnings("all")
    private void initLastModifiedTime(LastModifiedTime lastModifiedTime) {
        this.unique = lastModifiedTime.unique();
        this.nonnull = lastModifiedTime.nonnull();
        this.insertable = lastModifiedTime.insertable();
        this.updatable = lastModifiedTime.updatable();
        this.table = lastModifiedTime.table();
        this.column = lastModifiedTime.name();
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
    public Object get(Object target) {
        try {
            return getRequiredGetter().invoke(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(Object target, Object value) {
        try {
            getRequiredSetter().invoke(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Association<Property> createAssociation() {
        return null;
    }

}
