package org.ifinal.finalframework.context.exception.result;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.annotation.result.R;
import org.ifinal.finalframework.annotation.result.Result;
import org.ifinal.finalframework.context.exception.ServiceException;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(0)
@Component
public class IExceptionResultExceptionHandler implements ResultExceptionHandler<IException> {

    @Override
    public boolean supports(@NonNull Throwable throwable) {
        return throwable instanceof IException;
    }

    @Override
    @NonNull
    public Result<?> handle(@NonNull IException e) {
        if (e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            return R.failure(se.getStatus(), se.getDescription(), e.getCode(), e.getMessage());
        } else {
            return R.failure(500, e.getMessage(), e.getCode(), e.getMessage());
        }
    }
}
