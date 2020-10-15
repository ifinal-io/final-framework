package org.finalframework.web.converter;

import org.finalframework.auto.spring.factory.annotation.SpringConverter;
import org.finalframework.context.exception.NotFoundException;
import org.finalframework.core.converter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * convert the name to a {@link Class<?>}, throw a {@link NotFoundException} if the {@link Class} of name is not found.
 *
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
            throw new NotFoundException(e.getMessage());
        }
    }
}
