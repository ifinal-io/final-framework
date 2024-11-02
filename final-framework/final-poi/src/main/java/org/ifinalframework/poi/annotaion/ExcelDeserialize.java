package org.ifinalframework.poi.annotaion;

import org.springframework.core.annotation.AliasFor;

import org.ifinalframework.poi.databind.ExcelDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author iimik
 * @version 1.2.4
 **/
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDeserialize {

    @AliasFor("value")
    Class<? extends ExcelDeserializer> use() default ExcelDeserializer.class;

    @AliasFor("use")
    Class<? extends ExcelDeserializer> value() default ExcelDeserializer.class;


}
