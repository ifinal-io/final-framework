package cn.com.likly.finalframework.data.mapping.holder;

import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.annotation.Table;
import cn.com.likly.finalframework.data.annotation.enums.PrimaryKeyType;
import cn.com.likly.finalframework.util.Assert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
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
public class BaseEntityHolder<T, P extends PropertyHolder<P>> extends BasicPersistentEntity<T, P> implements EntityHolder<T, P> {
    private final List<P> properties = new ArrayList<>();
    @Getter
    private String table;
    @Getter
    private PrimaryKeyType primaryKeyType = PrimaryKeyType.AUTO_INC;

    private BaseEntityHolder(TypeInformation<T> information) {
        super(information);
    }

    public BaseEntityHolder(Class<T> entityClass) {
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
            } else if (isAnnotationPresent(javax.persistence.Table.class)) {
                this.table = findAnnotation(javax.persistence.Table.class).name();
            }

        } finally {
            if (Assert.isEmpty(table)) {
                this.table = entityClass.getSimpleName();
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
                    .map(it -> (P) new BasePropertyHolder<>(
                                    Property.of(getTypeInformation(), getField(it.getName(), entityClass), it),
                                    this,
                                    SimpleTypeHolder.DEFAULT
                            )
                    )
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

    private Field getField(String name, Class target) {
        if (target == Object.class) return null;
        try {
            return target.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(name, target.getSuperclass());
        }
    }


    @Override
    public String getTable() {
        return table;
    }

    @Override
    public Stream<P> stream() {
        return properties.stream();
    }
}
