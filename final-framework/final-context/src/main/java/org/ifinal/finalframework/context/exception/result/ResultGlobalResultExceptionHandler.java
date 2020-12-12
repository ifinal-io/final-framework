package org.ifinal.finalframework.context.exception.result;


import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.annotation.core.result.Result;
import org.ifinal.finalframework.context.exception.UnCatchException;
import org.ifinal.finalframework.context.exception.handler.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
//@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler implements GlobalExceptionHandler<Result<?>> {
    private static final Logger logger = LoggerFactory.getLogger(ResultGlobalResultExceptionHandler.class);

    private final List<ResultExceptionHandler<?>> resultExceptionHandlers = new ArrayList<>();

    public ResultGlobalResultExceptionHandler(final ObjectProvider<List<ResultExceptionHandler<?>>> resultExceptionHandlerObjectProvider) {

        this.resultExceptionHandlers.addAll(resultExceptionHandlerObjectProvider.getIfAvailable());
    }

    @Override
    public Result<?> handle(final @NonNull Throwable throwable) {

        if (throwable instanceof IException) {
            final IException e = (IException) throwable;
            logger.warn("==> exception: code={},message={}", e.getCode(), e.getMessage());
        } else {
            logger.error("==> ", throwable);
        }

        for (ResultExceptionHandler<?> resultExceptionHandler : resultExceptionHandlers) {
            if (resultExceptionHandler.supports(throwable)) {
                final Result<?> result = ((ResultExceptionHandler<Throwable>) resultExceptionHandler).handle(throwable);
                result.setTrace(MDC.get("trace"));
                result.setTimestamp(System.currentTimeMillis());
                result.setException(throwable.getClass());
                return result;
            }
        }

        throw new UnCatchException(throwable);

    }

}
