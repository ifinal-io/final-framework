package org.finalframework.data.exception.result;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Slf4j
@Order
@ResultExceptionHandler
public class IExceptionResultExceptionHandler implements org.finalframework.data.exception.result.ResultExceptionHandler<Throwable> {

    @Override
    public boolean supports(Throwable t) {
        return t instanceof IException;
    }

    @Override
    public Result handle(Throwable throwable) {
        if (throwable instanceof IException) {
            return R.failure(((IException) throwable).getCode(), throwable.getMessage(), ((IException) throwable).getToast());
        }
        throw new IllegalArgumentException("不支持异常处理：" + throwable.getClass());
    }
}
