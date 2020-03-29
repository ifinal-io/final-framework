package org.finalframework.data.exception.result;


import org.finalframework.data.exception.handler.AbsGlobalExceptionHandler;
import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 11:09:58
 * @since 1.0
 */
@SpringComponent
@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler extends AbsGlobalExceptionHandler<Result<?>> implements ApplicationListener<ApplicationReadyEvent> {


    private List<ResultExceptionHandler<Throwable>> resultExceptionHandlers;

    public ResultGlobalResultExceptionHandler(ObjectProvider<List<ResultExceptionHandler<Throwable>>> resultExceptionHandlerObjectProvider) {
        this.resultExceptionHandlers = resultExceptionHandlerObjectProvider.getIfAvailable();
    }


    @Override
    public Result<?> handle(Throwable throwable) {
        Result<?> result = super.handle(throwable);
        if (result != null) {
            result.setTrace(MDC.get("trace"));
            result.setTimestamp(System.currentTimeMillis());
            result.setException(throwable.getClass());
        }
        return result;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.resultExceptionHandlers.forEach(this::registerExceptionHandler);
    }
}
