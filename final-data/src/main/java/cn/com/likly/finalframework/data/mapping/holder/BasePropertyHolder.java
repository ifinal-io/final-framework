package cn.com.likly.finalframework.data.mapping.holder;

import cn.com.likly.finalframework.data.annotation.Column;
import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.annotation.enums.PersistentType;
import cn.com.likly.finalframework.util.Assert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:03
 * @since 1.0
 */
@Slf4j
@Getter
public class BasePropertyHolder<P extends PropertyHolder<P>> extends AnnotationBasedPersistentProperty<P> implements PropertyHolder<P> {

    private String table;
    private String column;
    private PersistentType persistentType = PersistentType.AUTO;
    private boolean unique = false;
    private boolean nullable = true;
    private boolean insertable = true;
    private boolean updatable = true;


    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public BasePropertyHolder(Property property, PersistentEntity<?, P> owner, SimpleTypeHolder simpleTypeHolder) {
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
                Column column = findAnnotation(Column.class);
                if (column != null) {
                    this.unique = column.unique();
                    this.nullable = column.nullable();
                    this.insertable = column.insertable();
                    this.updatable = column.updatable();
                    this.table = column.table();
                    this.column = column.name();
                }

            }
            if (isAnnotationPresent(javax.persistence.Column.class)) {
                javax.persistence.Column column = findAnnotation(javax.persistence.Column.class);
                if (column != null) {
                    this.unique = column.unique();
                    this.nullable = column.nullable();
                    this.insertable = column.insertable();
                    this.updatable = column.updatable();
                    this.table = column.table();
                    this.column = column.name();
                }
            }


        } finally {


            if (isIdProperty()) {
                unique = true;
                nullable = false;
                updatable = false;

                if (isAnnotationPresent(PrimaryKey.class)) {
                    this.column = findAnnotation(PrimaryKey.class).name();
                }

            }


            if (Assert.isEmpty(this.column)) {
                this.column = getName();
            }
        }


    }


    @Override
    public boolean unique() {
        return unique;
    }

    @Override
    public boolean nullable() {
        return nullable;
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
    protected Association<P> createAssociation() {
        return null;
    }

}
