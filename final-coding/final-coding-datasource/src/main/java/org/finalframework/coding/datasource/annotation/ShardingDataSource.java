package org.finalframework.coding.datasource.annotation;

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
public @interface ShardingDataSource {

    /**
     * 数据源配置前缀，参考 spring.datasource
     */
    String prefix() default "final.sharding";

    String[] value();

    /**
     * 是否为主要的数据源，默认为{@code false}，当配置为 {@code true}时，会在生成的代码中添加 {@link Primary}注解。
     *
     * @see Primary
     */
    boolean primary() default false;
}
