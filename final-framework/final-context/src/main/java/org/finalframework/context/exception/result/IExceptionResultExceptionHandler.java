package org.finalframework.context.exception.result;

import org.finalframework.annotation.IException;
import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.context.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Order(0)
@Component
public class IExceptionResultExceptionHandler implements ResultExceptionHandler<IException> {

    private static final Logger logger = LoggerFactory.getLogger(IExceptionResultExceptionHandler.class);


    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof IException;
    }


    public Result<?> handle(ServiceException e) {
        return R.failure(e.getStatus(), e.getDescription(), e.getCode(), e.getMessage());
    }

    @Override
    public Result<?> handle(IException e) {
        if (e instanceof ServiceException) {
            return handle((ServiceException) e);
        } else {
            return R.failure(500, e.getMessage(), e.getCode(), e.getMessage());
        }
    }
}
