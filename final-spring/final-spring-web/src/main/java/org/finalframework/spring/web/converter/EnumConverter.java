package org.finalframework.spring.web.converter;


import org.finalframework.core.Assert;
import org.finalframework.data.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 枚举类型转换器，实现将 {@link String} 映射到 {@link Enum} 类型，该枚举类型需要实现 {@link IEnum}接口。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-11 15:58:57
 * @since 1.0
 */
public class EnumConverter<T extends IEnum> implements Converter<String, T> {
    private final Class<T> enumType;
    private final Map<String, T> enumMap;

    public EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
        final T[] enums = this.enumType.getEnumConstants();
        enumMap = Arrays.stream(enums).collect(Collectors.toMap(e -> e.getCode().toString(), e -> e));

    }

    @Override
    public T convert(String source) {
        return Assert.isBlank(source) ? null : enumMap.get(source.trim());
    }

    @Override
    public String toString() {
        return enumType.getSimpleName() + "Converter";
    }
}
