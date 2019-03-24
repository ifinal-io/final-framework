package org.finalframework.spring.web.exception;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.spring.util.BeanUtils;
import org.finalframework.spring.web.exception.annotation.RestExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@Slf4j
public class DefaultGlobalExceptionHandler<T> implements GlobalExceptionHandler<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private List<ExceptionHandlerBean<T>> exceptionHandlerBeans;
    @Setter
    private org.finalframework.spring.web.exception.RestExceptionHandler<T> unCatchExceptionHandler;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        this.exceptionHandlerBeans = BeanUtils.findAllBeansAnnotatedBy(applicationContext, RestExceptionHandler.class)
                .map(it -> {
                    if (!(it instanceof ExceptionHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
                    }
                    Order order = it.getClass().getAnnotation(Order.class);
                    return new ExceptionHandlerBean<>(order == null ? 0 : order.value(), (ExceptionHandler<T>) it);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    @ResponseBody
    public T handle(Throwable throwable) throws Throwable {
        for (ExceptionHandlerBean<T> item : exceptionHandlerBeans) {
            if (item.supports(throwable)) {
                return item.handle(throwable);
            }
        }

        if (unCatchExceptionHandler != null && unCatchExceptionHandler.supports(throwable)) {
            return unCatchExceptionHandler.handle(throwable);
        }


        logger.error("UnCatchException", throwable);
        throw throwable;
    }
}
