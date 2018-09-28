package cn.com.likly.finalframework.mybatis.annotation;

import cn.com.likly.finalframework.mybatis.annotation.enums.PrimaryKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:50
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String table() default "";

    String alias() default "";

    PrimaryKey primaryKey() default PrimaryKey.AUTO;
}
