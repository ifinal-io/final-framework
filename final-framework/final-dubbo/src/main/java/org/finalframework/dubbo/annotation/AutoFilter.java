package org.finalframework.dubbo.annotation;

import org.apache.dubbo.rpc.Filter;
import org.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-08-25 19:42:27
 * @since 1.0
 */
@AutoService(
        value = Filter.class,
        path = "dubbo"
)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AutoFilter {
    String value();
}
