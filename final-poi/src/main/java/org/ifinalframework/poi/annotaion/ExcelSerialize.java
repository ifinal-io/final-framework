package org.ifinalframework.poi.annotaion;

import org.ifinalframework.poi.ExcelSerializer;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.2.4
 **/
public @interface ExcelSerialize {
    @AliasFor("value")
    Class<? extends ExcelSerializer> use() default ExcelSerializer.class;

    @AliasFor("use")
    Class<? extends ExcelSerializer> value() default ExcelSerializer.class;
}
