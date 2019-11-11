package org.finalframework.data.exception.handler;


import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.UnCatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 10:53:03
 * @since 1.0
 */
public class AbsGlobalExceptionHandler<T> implements GlobalExceptionHandler<T> {
    private final Logger logger;
    private final List<ExceptionHandlerBean<T>> exceptionHandlerBeans = new CopyOnWriteArrayList<>();

    private ExceptionHandler<Throwable, T> unCatchExceptionHandler;

    public AbsGlobalExceptionHandler(String logger) {
        this.logger = LoggerFactory.getLogger(logger);
    }

    public AbsGlobalExceptionHandler() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void registerExceptionHandler(ExceptionHandler<Throwable, T> handler) {
        Order order = handler.getClass().getAnnotation(Order.class);
        this.exceptionHandlerBeans.add(new ExceptionHandlerBean<>(order == null ? 0 : order.value(), handler));
        Collections.sort(exceptionHandlerBeans);
    }

    @Override
    public void setUnCatchExceptionHandler(ExceptionHandler<Throwable, T> handler) {
        this.unCatchExceptionHandler = handler;
    }

    @Override
    public T handle(Throwable throwable) {

        if (throwable instanceof IException) {
            final IException e = (IException) throwable;
            logger.warn("==> exception: code={},message={},toast={}", e.getCode(), e.getMessage(), e.getToast());
        } else {
            logger.error("==> ", throwable);
        }

        for (ExceptionHandlerBean<T> item : exceptionHandlerBeans) {
            if (item.supports(throwable)) {
                return item.handle(throwable);
            }
        }

        if (unCatchExceptionHandler != null && unCatchExceptionHandler.supports(throwable)) {
            return unCatchExceptionHandler.handle(throwable);
        }

        throw new UnCatchException(throwable);
    }
}
