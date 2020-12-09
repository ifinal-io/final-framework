package org.ifinal.finalframework.web.converter;


import org.ifinal.finalframework.annotation.core.IEnum;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings("rawtypes")
public class EnumConverterFactory implements ConverterFactory<String, IEnum> {

    @NonNull
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new EnumConverter<>(targetType);
    }

    /**
     * 枚举类型转换器，实现将 {@link String} 映射到 {@link Enum} 类型，该枚举类型需要实现 {@link IEnum}接口。
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    public static class EnumConverter<T extends IEnum> implements Converter<String, T> {
        private final Class<T> enumType;
        private final Map<String, T> enumMap;

        public EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
            final T[] enums = this.enumType.getEnumConstants();
            enumMap = Arrays.stream(enums).collect(Collectors.toMap(e -> e.getCode().toString(), e -> e));

        }

        @Override
        public T convert(@NonNull String source) {
            return Asserts.isBlank(source) ? null : enumMap.get(source.trim());
        }

        @Override
        public String toString() {
            return enumType.getSimpleName() + "Converter";
        }
    }
}
