package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.annotation.NotColumn;
import cn.com.likly.finalframework.mybatis.annotation.Table;
import cn.com.likly.finalframework.mybatis.annotation.enums.PrimaryKey;
import cn.com.likly.finalframework.mybatis.configurer.NameConverterConfigurer;
import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import cn.com.likly.finalframework.mybatis.holder.PropertyHolder;
import cn.com.likly.finalframework.mybatis.holder.TableHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:19
 * @since 1.0
 */
@Slf4j
@Component
public class DefaultEntityHolderFactory implements EntityHolderFactory {

    @Resource
    private NameConverterConfigurer nameConverterConfigurer;
    @Resource
    private PropertyHolderFactory propertyHolderFactory;

    @Override
    public <ID, T extends Entity<ID>> EntityHolder create(Class<T> entityClass) {
        logger.debug("==> create entityHolder of {}", entityClass.getName());

        final TableHolder tableHolder = buildTableHolder(entityClass);
        logger.debug("==> build tableHolder: {}", tableHolder);
        List<PropertyHolder> propertyHolders = buildPropertyHolders(tableHolder, entityClass);
        PropertyHolder primaryKeyHolder = null;
        for (PropertyHolder propertyHolder : propertyHolders) {
            if (propertyHolder.getPrimaryKey()) {
                if (primaryKeyHolder == null) {
                    primaryKeyHolder = propertyHolder;
                } else {
                    throw new IllegalArgumentException("have more than  one primary key");
                }
            }
        }

        return EntityHolder.builder()
                .entityClass(entityClass)
                .table(tableHolder)
                .primaryKey(primaryKeyHolder)
                .properties(propertyHolders)
                .build();
    }


    private TableHolder buildTableHolder(Class entityClass) {
        Table annotation = (Table) entityClass.getAnnotation(Table.class);
        return TableHolder.builder()
                .table(nameConverterConfigurer.getTableNameConverter().convert(
                        annotation == null ? entityClass.getSimpleName()
                                : annotation.table().trim().isEmpty() ? entityClass.getSimpleName()
                                : annotation.table().trim()
                ))
                .alias(
                        annotation == null || annotation.alias().trim().isEmpty() ? null : annotation.alias().trim()
                )
                .primaryKey(
                        annotation == null ? PrimaryKey.AUTO : annotation.primaryKey()
                )
                .build();
    }

    private List<PropertyHolder> buildPropertyHolders(TableHolder tableHolder, Class entityClass) {
        List<PropertyHolder> reslut = new ArrayList<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                String name = descriptor.getName();
                if (name.equals("class")) continue;
                final Field field = getField(name, entityClass);
                if (field == null) continue;
                if (field.getAnnotation(NotColumn.class) != null) continue;

                reslut.add(propertyHolderFactory.create(tableHolder, field, descriptor.getReadMethod()));
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }


        return reslut;
    }

    private Field getField(String name, Class target) {
        if (target == Object.class) return null;
        try {
            return target.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(name, target.getSuperclass());
        }
    }


}
