package com.ilikly.finalframework.spring.web.converter;

import com.ilikly.finalframework.core.formatter.DateFormatters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:32
 * @since 1.0
 */
@Slf4j
@Component
public class String2DateConverter implements Converter<String, Date> {


    @Override
    public Date convert(String source) {
        return DateFormatters.DEFAULT.parse(source);
    }
}
