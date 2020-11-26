package org.ifinal.finalframework.web.converter;

import org.aspectj.lang.annotation.Aspect;
import org.ifinal.finalframework.context.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class String2ClassConverter implements Converter<String, Class<?>> {

    public static final Logger logger = LoggerFactory.getLogger(String2ClassConverter.class);

    private static final Map<String, Class<? extends Annotation>> annotations
            = Stream.of(
            RestController.class, Controller.class,
            Component.class, Service.class, Configuration.class,
            EnableAutoConfiguration.class, Aspect.class
    ).collect(Collectors.toMap(Class::getSimpleName, Function.identity()));


    @Override
    public Class<?> convert(@NonNull String source) {
        try {
            return annotations.containsKey(source) ? annotations.get(source) : Class.forName(source);
        } catch (ClassNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
