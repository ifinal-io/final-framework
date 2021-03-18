package org.ifinal.finalframework.dubbo.annotation;

import org.ifinal.auto.service.annotation.AutoService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.dubbo.rpc.Filter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(
    value = Filter.class,
    path = "dubbo",
    ignore = true
)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AutoFilter {

    String value();

}
