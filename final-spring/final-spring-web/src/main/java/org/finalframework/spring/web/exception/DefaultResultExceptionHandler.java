package org.finalframework.spring.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.data.exception.IException;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.web.exception.annotation.RestExceptionHandler;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Slf4j
@Order
@RestExceptionHandler
public class DefaultResultExceptionHandler implements ResultExceptionHandler {

    @Override
    public boolean supports(Throwable t) {
        return t instanceof IException;
    }

    @Override
    public Result handle(Throwable throwable) {
        if (throwable instanceof IException) {
            return R.failure(((IException) throwable).getCode(), throwable.getMessage());
        }
        throw new IllegalArgumentException("不支持异常处理：" + throwable.getClass());
    }
}
