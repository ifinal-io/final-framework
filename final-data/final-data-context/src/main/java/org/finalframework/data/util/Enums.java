package org.finalframework.data.util;


import org.finalframework.data.entity.enums.YN;
import org.finalframework.data.annotation.EnumValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:18:59
 * @since 1.0
 */
public class Enums {

    public static <E extends Enum<E>> E findEnum(EnumValue ann, Object value) {
        return findEnum(ann.value(), ann.creator(), ann.valueType(), value);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E findEnum(Class<? extends Enum<?>> clazz, String creator, Class<?> valueType, Object value) {
        Method valueOf = ReflectionUtils.findMethod(clazz, creator, valueType);

        if (valueOf == null) {
            throw new IllegalArgumentException("can not find method named " + creator + " with type of " + valueType.getCanonicalName() + " as enum class " + clazz.getCanonicalName());
        }

        return (E) ReflectionUtils.invokeMethod(valueOf, clazz, value);
    }


    public static void main(String[] args) {

        System.out.println(Enums.findEnum(YN.class, "valueOf", int.class, 1));
    }

}

