package org.finalframework.data.exception.result;


import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.exception.handler.AbsGlobalExceptionHandler;
import org.finalframework.data.exception.handler.ExceptionHandler;
import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.finalframework.data.result.Result;
import org.finalframework.data.util.BeanUtils;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 11:09:58
 * @since 1.0
 */
@Component
@SpringFactory(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler extends AbsGlobalExceptionHandler<Result>
        implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    public ResultGlobalResultExceptionHandler(){
    }

    public ResultGlobalResultExceptionHandler(String logger) {
        super(logger);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanUtils.findAllBeansAnnotatedBy(applicationContext, ResultExceptionHandler.class)
                .map(it -> {
                    if (!(it instanceof ExceptionHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
                    }
                    return (ExceptionHandler) it;
                }).forEach(this::registerExceptionHandler);
    }

    @Override
    public Result handle(Throwable throwable) {
        Result result = super.handle(throwable);
        if (result != null) {
            result.setTrace(MDC.get("trace"));
            result.setTimestamp(System.currentTimeMillis());
        }
        return result;
    }
}
