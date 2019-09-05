package org.finalframework.data.exception.annotation;

import org.finalframework.data.exception.result.IExceptionResultExceptionHandler;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.data.exception.result.UnCatchResultExceptionHandler;
import org.finalframework.data.exception.result.ViolationResultExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被该注解所标记的元素，将会被注册到 {@link ResultGlobalResultExceptionHandler}全局异常处理Handler中。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:15
 * @see ViolationResultExceptionHandler
 * @see IExceptionResultExceptionHandler
 * @see UnCatchResultExceptionHandler
 * @see ResultGlobalResultExceptionHandler
 * @since 1.0
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultExceptionHandler {

}
