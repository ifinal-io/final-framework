package org.finalframework.web.converter;

import org.finalframework.auto.spring.factory.annotation.SpringConverter;
import org.finalframework.core.formatter.DateFormatters;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:32
 * @since 1.0
 */
@SpringConverter
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        return DateFormatters.DEFAULT.parse(source);
    }
}
