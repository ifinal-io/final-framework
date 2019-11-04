package org.finalframework.coding.datasource;

import org.springframework.context.annotation.Primary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源配置
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:11
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DataSource {
    /**
     * 数据源名称，默认为类名（不包含'DataSource'）。
     */
    String name() default "";

    /**
     * 数据源配置前缀，参考 spring.datasource
     */
    String prefix();

    /**
     * Mapper所在的包路径
     */
    String[] basePackages();

    /**
     * mapper.xml所有的文件路径
     */
    String[] mapperLocations() default {};

    /**
     * 是否为主要的数据源，默认为{@code false}，当配置为 {@code true}时，会在生成的代码中添加 {@link Primary}注解。
     *
     * @see Primary
     */
    boolean primary() default false;
}
