package org.finalframework.spring.web.converter;

import org.finalframework.core.converter.Converter;
import org.finalframework.data.exception.NotFoundException;
import org.finalframework.spring.annotation.factory.SpringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-11 09:59:53
 * @since 1.0
 */
@SpringConverter
public class String2ClassConverter implements Converter<String, Class<?>> {

    public static final Logger logger = LoggerFactory.getLogger(String2ClassConverter.class);

    @Override
    public Class<?> convert(String source) {
        try {
            return Class.forName(source);
        } catch (ClassNotFoundException e) {
            throw new NotFoundException("Class Not Found for clazz=%s", source);
        }
    }
}
