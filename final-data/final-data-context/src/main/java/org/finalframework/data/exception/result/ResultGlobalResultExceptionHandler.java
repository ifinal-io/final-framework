package org.finalframework.data.exception.result;


import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.UnCatchException;
import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 11:09:58
 * @since 1.0
 */
@SpringComponent
@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler implements GlobalExceptionHandler<Result<?>> {
    private static final Logger logger = LoggerFactory.getLogger(ResultGlobalResultExceptionHandler.class);
    private List<ResultExceptionHandler<Throwable>> resultExceptionHandlers;

    public ResultGlobalResultExceptionHandler(ObjectProvider<List<ResultExceptionHandler<Throwable>>> resultExceptionHandlerObjectProvider) {
        this.resultExceptionHandlers = resultExceptionHandlerObjectProvider.getIfAvailable();
    }

    @Override
    public Result<?> handle(Throwable throwable) {

        if (throwable instanceof IException) {
            final IException e = (IException) throwable;
            logger.warn("==> exception: code={},message={}", e.getCode(), e.getMessage());
        } else {
            logger.error("==> ", throwable);
        }

        for (ResultExceptionHandler<Throwable> resultExceptionHandler : resultExceptionHandlers) {
            if (resultExceptionHandler.supports(throwable)) {
                final Result<?> result = resultExceptionHandler.handle(throwable);
                result.setTrace(MDC.get("trace"));
                result.setTimestamp(System.currentTimeMillis());
                result.setException(throwable.getClass());
                return result;
            }
        }

        throw new UnCatchException(throwable);

    }

}
