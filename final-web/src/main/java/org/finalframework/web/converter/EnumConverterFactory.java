package org.finalframework.web.converter;


import org.finalframework.annotation.IEnum;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.finalframework.core.Asserts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 15:56:02
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
@SpringFactory(ConverterFactory.class)
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
     * @version 1.0
     * @date 2019-03-11 15:58:57
     * @since 1.0
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
