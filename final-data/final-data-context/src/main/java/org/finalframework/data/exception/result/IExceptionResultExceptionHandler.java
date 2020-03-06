package org.finalframework.data.exception.result;

import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.ServiceException;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@ResultExceptionHandler
public class IExceptionResultExceptionHandler implements org.finalframework.data.exception.result.ResultExceptionHandler<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(IExceptionResultExceptionHandler.class);

    @Override
    public boolean supports(Throwable t) {
        return t instanceof IException;
    }

    @Override
    public Result<?> handle(Throwable throwable) {
        if (throwable instanceof ServiceException) {
            return handle((ServiceException) throwable);
        } else if (throwable instanceof IException) {
            return handle((IException) throwable);
        }
        throw new IllegalArgumentException("不支持异常处理：" + throwable.getClass());
    }

    public Result<?> handle(ServiceException e) {
        return R.failure(e.getStatus(), e.getDescription(), e.getCode(), e.getMessage());
    }

    public Result<?> handle(IException e) {
        return R.failure(500, e.getMessage(), e.getCode(), e.getMessage());
    }
}
