package org.ifinal.finalframework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Beans {

    private static final Map<Class<?>, BeanInfo> BEAN_INFO_MAP = new ConcurrentHashMap<>();

    private Beans() {
    }

    public static BeanInfo from(final Class<?> bean) {
        return BEAN_INFO_MAP.computeIfAbsent(bean, key -> {
            try {
                return Introspector.getBeanInfo(key);
            } catch (IntrospectionException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    public static Map<String, Object> toMap(final Object bean) {
        BeanInfo beanInfo = from(bean.getClass());
        return Arrays.stream(beanInfo.getPropertyDescriptors())
            //.filter(propertyDescriptor -> propertyDescriptor.getName().equals("schema"))
            .collect(Collectors.toMap(PropertyDescriptor::getName, property -> {
                try {
                    return property.getReadMethod().invoke(bean);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }));
    }

}

