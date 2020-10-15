package org.finalframework.web.converter;


import org.finalframework.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 15:56:02
 * @since 1.0
 */
public class EnumConverterFactory implements ConverterFactory<String, IEnum> {

    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumConverter<>(targetType);
    }
}
