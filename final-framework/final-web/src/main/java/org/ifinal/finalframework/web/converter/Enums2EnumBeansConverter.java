package org.ifinal.finalframework.web.converter;

import org.ifinal.finalframework.context.util.Messages;
import org.ifinal.finalframework.core.annotation.Enums;
import org.ifinal.finalframework.core.annotation.IEnum;
import org.ifinal.finalframework.util.Asserts;
import org.ifinal.finalframework.util.function.Converter;
import org.ifinal.finalframework.util.function.Filter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Enums2EnumBeansConverter implements Converter<Object, List<Map<String, Object>>>, Filter<Object> {

    @Override
    public boolean matches(Object enums) {


        if (Asserts.isNull(enums)) return false;

        if (enums.getClass().isArray()) {
            if (Asserts.isEmpty(enums)) return false;
            return ((Object[]) enums)[0] instanceof IEnum;
        }

        if (enums instanceof List) {
            if (Asserts.isEmpty(enums)) return false;
            return ((List<?>) enums).get(0) instanceof IEnum;
        }

        if (enums instanceof Set) {
            if (Asserts.isEmpty(enums)) return false;
            return ((Set<?>) enums).toArray()[0] instanceof IEnum;
        }

        return enums instanceof Class && IEnum.class.isAssignableFrom((Class<?>) enums);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> convert(Object body) {

        if (body instanceof Collection) {
            Collection<IEnum<?>> enums = (Collection<IEnum<?>>) body;
            return enums.stream().map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body.getClass().isArray()) {
            IEnum<?>[] enums = (IEnum<?>[]) body;
            return Arrays.stream(enums).map(this::buildEnumBean).collect(Collectors.toList());
        } else if (body instanceof Class && IEnum.class.isAssignableFrom((Class<?>) body)) {
            final Class<Enum<?>> enumClass = (Class<Enum<?>>) body;
            return Arrays.stream(enumClass.getEnumConstants())
                    .map(item -> this.buildEnumBean((IEnum<?>) item))
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("不支持的数据类型：" + body.getClass());
    }

    private Map<String, Object> buildEnumBean(IEnum<?> item) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", item.getCode());
        if (item instanceof Enum) {
            result.put("desc", Messages.getMessage(Enums.getEnumI18NCode((Enum<?>) item), item.getDesc()));
            result.put("name", ((Enum<?>) item).name());
        } else {
            result.put("desc", item.getDesc());
        }
        return result;
    }

}
