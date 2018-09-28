package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.annotation.*;
import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;
import cn.com.likly.finalframework.mybatis.configurer.NameConverterConfigurer;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import cn.com.likly.finalframework.mybatis.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.holder.TableHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 12:58
 * @since 1.0
 */
@Component
public class DefaultPropertyHolderFactory implements PropertyHolderFactory {

    @Resource
    private NameConverterConfigurer nameConverterConfigurer;
    @Resource
    private TypeHandlerRegistry typeHandlerRegistry;

    @Override
    public <T extends Entity> PropertyHolder create(TableHolder tableHolder, Field field, Method getter) {

        PropertyHolder.PropertyHolderBuilder builder = PropertyHolder.builder();
        builder.table(
                tableHolder.getAlias() == null || tableHolder.getAlias().isEmpty() ?
                        tableHolder.getTable().trim() : tableHolder.getAlias().trim()
        )
                .property(field.getName())
                .column(nameConverterConfigurer.getPropertyNameConverter().convert(field.getName()))
                .primaryKey(false)
                .notnull(false)
                .unique(false)
                .insert(true)
                .update(true)
                .select(true)
                .description(null);

        JdbcType jdbcType = JdbcType.DEFAULT;

        if (field.getAnnotation(ID.class) != null) {
            jdbcType = this.build(builder, field.getAnnotation(ID.class));
        } else if (field.getAnnotation(TableColumn.class) != null) {
            jdbcType = this.build(builder, field.getAnnotation(TableColumn.class));
        } else if (field.getAnnotation(JsonColumn.class) != null) {
            jdbcType = this.build(builder, field.getAnnotation(JsonColumn.class));
        } else if (field.getAnnotation(CreateTime.class) != null) {
            jdbcType = this.build(builder, field.getAnnotation(CreateTime.class));
        } else if (field.getAnnotation(UpdateTime.class) != null) {
            jdbcType = this.build(builder, field.getAnnotation(UpdateTime.class));
        }

        Class<?> fieldType = field.getType();
        Class<?> javaType = fieldType;
        Class<?> collectionType = null;
        if (Collection.class.isAssignableFrom(fieldType)) {
            collectionType = fieldType;
            javaType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        }

        builder.javaType(javaType)
                .jdbcType(jdbcType)
                .typeHandler(typeHandlerRegistry.getTypeHandler(javaType, collectionType, jdbcType));


        return builder.build();
    }

    private JdbcType build(PropertyHolder.PropertyHolderBuilder builder, ID id) {
        if (!id.column().trim().isEmpty()) {
            builder.column(id.column().trim());
        }

        builder.primaryKey(true)
                .jdbcType(id.jdbcType())
                .notnull(id.notnull())
                .unique(id.unique())
                .insert(id.insert())
                .update(id.update())
                .select(id.select())
                .description(id.description());

        return id.jdbcType();
    }

    private JdbcType build(PropertyHolder.PropertyHolderBuilder builder, TableColumn tableColumn) {
        if (!tableColumn.column().trim().isEmpty()) {
            builder.column(tableColumn.column().trim());
        }

        builder.primaryKey(false)
                .jdbcType(tableColumn.jdbcType())
                .notnull(tableColumn.notnull())
                .unique(tableColumn.unique())
                .insert(tableColumn.insert())
                .update(tableColumn.update())
                .select(tableColumn.select())
                .description(tableColumn.description());

        return tableColumn.jdbcType();
    }

    private JdbcType build(PropertyHolder.PropertyHolderBuilder builder, JsonColumn tableColumn) {
        if (!tableColumn.column().trim().isEmpty()) {
            builder.column(tableColumn.column().trim());
        }

        builder.primaryKey(false)
                .jdbcType(JdbcType.JSON)
                .notnull(tableColumn.notnull())
                .unique(tableColumn.unique())
                .insert(tableColumn.insert())
                .update(tableColumn.update())
                .select(tableColumn.select())
                .description(tableColumn.description());

        return JdbcType.JSON;
    }

    private JdbcType build(PropertyHolder.PropertyHolderBuilder builder, CreateTime createTime) {
        if (!createTime.column().trim().isEmpty()) {
            builder.column(createTime.column().trim());
        }

        builder.primaryKey(false)
                .jdbcType(createTime.jdbcType())
                .notnull(createTime.notnull())
                .unique(createTime.unique())
                .insert(createTime.insert())
                .update(createTime.update())
                .select(createTime.select())
                .description(createTime.description());

        return createTime.jdbcType();
    }

    private JdbcType build(PropertyHolder.PropertyHolderBuilder builder, UpdateTime updateTime) {
        if (!updateTime.column().trim().isEmpty()) {
            builder.column(updateTime.column().trim());
        }

        builder.primaryKey(false)
                .jdbcType(updateTime.jdbcType())
                .notnull(updateTime.notnull())
                .unique(updateTime.unique())
                .insert(updateTime.insert())
                .update(updateTime.update())
                .select(updateTime.select())
                .description(updateTime.description());

        return updateTime.jdbcType();
    }
}
