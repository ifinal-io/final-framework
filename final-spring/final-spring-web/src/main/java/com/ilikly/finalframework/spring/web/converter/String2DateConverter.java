package com.ilikly.finalframework.spring.web.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
    private static final DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:dd");


    @Override
    public Date convert(String source) {

        if (source == null) return null;
        try {

            if (source.contains("-")) {
                if (source.contains(":")) {
                    return dateTimeFormat.parse(source);
                } else {
                    return dateFormat.parse(source);
                }
            } else if (source.contains("/")) {
                if (source.contains(":")) {
                    return dateTimeFormat2.parse(source);
                } else {
                    return dateFormat2.parse(source);
                }
            }
        } catch (ParseException e) {
            logger.error("date converter exception: source={}", source);
        }


        return null;
    }
}
