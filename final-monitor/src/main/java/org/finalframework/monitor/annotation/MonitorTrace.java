

package org.finalframework.monitor.annotation;

import org.finalframework.monitor.executor.Tracer;
import org.finalframework.monitor.handler.TraceOperationHandler;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.AdviceAnnotation;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 在方法执行之前，向上下文中注入{@code trace}链路追踪变量。
 *
 * @author likly
 * @version 1.0
 * @date 2019-07-01 13:10:18
 * @since 1.0
 */
@AdviceAnnotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorTrace {

    @AliasFor("trace")
    String value() default "trace";

    @AliasFor("value")
    String trace() default "trace";

    Class<? extends OperationHandler> handler() default TraceOperationHandler.class;

    Class<? extends Executor> executor() default Tracer.class;
}
