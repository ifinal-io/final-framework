package org.ifinal.finalframework.annotation.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.aop.AopAnnotation;
import org.springframework.core.annotation.AliasFor;

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
