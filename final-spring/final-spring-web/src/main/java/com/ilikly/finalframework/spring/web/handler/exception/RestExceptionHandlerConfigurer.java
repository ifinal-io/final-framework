package com.ilikly.finalframework.spring.web.handler.exception;

import com.ilikly.finalframework.spring.util.BeanUtils;
import com.ilikly.finalframework.spring.web.handler.exception.annotation.RestExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
@RestControllerAdvice
public class RestExceptionHandlerConfigurer implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private List<ExceptionHandlerBean> exceptionHandlerBeans;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        this.exceptionHandlerBeans = BeanUtils.findAllBeansAnnotatedBy(applicationContext, RestExceptionHandler.class)
                .map(it -> {

                    if (!(it instanceof ExceptionHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
                    }

                    Order order = it.getClass().getAnnotation(Order.class);
                    return new ExceptionHandlerBean(order == null ? 0 : order.value(), (com.ilikly.finalframework.spring.web.handler.exception.ExceptionHandler<Throwable, Object>) it);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Object handlerException(Exception e) throws Throwable {
        for (ExceptionHandlerBean item : exceptionHandlerBeans) {
            if (item.supports(e)) {
                return item.handle(e);
            }
        }
        logger.error("UnCatchException", e);
        throw e;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
