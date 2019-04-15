package org.finalframework.data.exception.result;


import org.finalframework.data.exception.AbsGlobalExceptionHandler;
import org.finalframework.data.exception.ExceptionHandler;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.result.Result;
import org.finalframework.data.util.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 11:09:58
 * @since 1.0
 */
public class ResultGlobalResultExceptionHandler extends AbsGlobalExceptionHandler<Result> implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public ResultGlobalResultExceptionHandler(String logger) {
        super(logger);
    }

    public ResultGlobalResultExceptionHandler() {
    }

    @PostConstruct
    public void init() {
        BeanUtils.findAllBeansAnnotatedBy(applicationContext, ResultExceptionHandler.class)
                .map(it -> {
                    if (!(it instanceof ExceptionHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
                    }
                    return (ExceptionHandler) it;
                }).forEach(this::registerExceptionHandler);

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
