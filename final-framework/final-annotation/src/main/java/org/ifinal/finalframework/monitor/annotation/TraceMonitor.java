package org.ifinal.finalframework.monitor.annotation;

import org.ifinal.finalframework.aop.annotation.AopAnnotation;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 在方法执行之前，向上下文中注入{@code trace}链路追踪变量。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AopAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TraceMonitor {

    @AliasFor("trace")
    String value() default "trace";

    @AliasFor("value")
    String trace() default "trace";

}
