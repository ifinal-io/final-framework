package cn.com.likly.finalframework.data.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:11
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DataSource {
    String prefix();
    String[] basePackages();
    String mapperLocations();
}
