

package org.finalframework.annotation;


import org.finalframework.annotation.data.EnumValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:18:59
 * @since 1.0
 */
public interface Enums {

    static <E extends Enum<E>> E findEnum(EnumValue ann, Object value) {
        return findEnum(ann.value(), ann.creator(), ann.valueType(), value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <E extends Enum<E>> E findEnum(Class<? extends Enum> clazz, String creator, Class<?> valueType, Object value) {
        Method valueOf = ReflectionUtils.findMethod(clazz, creator, valueType);

        if (valueOf == null) {
            throw new IllegalArgumentException("can not find method named " + creator + " with type of " + valueType.getCanonicalName() + " as enum class " + clazz.getCanonicalName());
        }

        return (E) ReflectionUtils.invokeMethod(valueOf, clazz, value);
    }

    static String getEnumI18NCode(Enum<?> value) {
        return String.format("%s.%s", value.getClass().getCanonicalName(), value.name().toLowerCase(Locale.ENGLISH));
    }


}

