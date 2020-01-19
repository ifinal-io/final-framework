package org.finalframework.coding.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-16 14:24:28
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MapperScan {

    /**
     * Mapper所在的包路径
     */
    String[] basePackages();

    /**
     * mapper.xml所有的文件路径
     */
    String[] mapperLocations() default {};
}
