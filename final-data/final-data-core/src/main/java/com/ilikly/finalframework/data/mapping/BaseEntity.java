package com.ilikly.finalframework.data.mapping;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.annotation.PrimaryKey;
import com.ilikly.finalframework.data.annotation.Table;
import com.ilikly.finalframework.data.annotation.enums.PrimaryKeyType;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 11:06
 * @since 1.0
 */
@Slf4j
public class BaseEntity<T> extends BasicPersistentEntity<T, Property> implements Entity<T> {
    private final List<Property> properties = new ArrayList<>();
    @Getter
    private String table;
    @Getter
    private PrimaryKeyType primaryKeyType = PrimaryKeyType.AUTO_INC;

    private BaseEntity(TypeInformation<T> information) {
        super(information);
    }

    public BaseEntity(Class<T> entityClass) {
        this(ClassTypeInformation.from(entityClass));
        init();
    }

    private void init() {
        initTable();
        initProperties();
    }

    private void initTable() {
        final Class entityClass = getType();
        try {
            if (isAnnotationPresent(Table.class)) {
                this.table = findAnnotation(Table.class).value();
            }
        } finally {
            if (Assert.isEmpty(table)) {
                this.table = NameConverterRegistry.getInstance().getTableNameConverter().convert(entityClass.getSimpleName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initProperties() {
        try {
            final Class entityClass = getType();
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
            Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(it -> !it.getName().equals("class"))
                    .map(it -> buildProperty(entityClass,it))
                    .forEach(it -> {
                        addPersistentProperty(it);
                        properties.add(it);

                        if (it.isIdProperty()) {
                            if (it.isAnnotationPresent(PrimaryKey.class)) {
                                this.primaryKeyType = it.findAnnotation(PrimaryKey.class).type();
                            }
                        }


                    });

            System.out.println();

        } catch (IntrospectionException e) {
            logger.error("", e);
        }
    }

    private Property buildProperty(Class entityClass, PropertyDescriptor descriptor) {
        final Field field = getField(descriptor.getName(), entityClass);
        return field == null
                ? new BaseProperty(org.springframework.data.mapping.model.Property.of(getTypeInformation(), descriptor), this, SimpleTypeHolder.DEFAULT)
                : new BaseProperty(org.springframework.data.mapping.model.Property.of(getTypeInformation(), field, descriptor), this, SimpleTypeHolder.DEFAULT);
    }

    private Field getField(String name, Class target) {
        if (target == Object.class)
            return null;
        try {
            return target.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(name, target.getSuperclass());
        }
    }


    @Override
    public Stream<Property> stream() {
        return properties.stream();
    }
}
