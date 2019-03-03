package com.ilikly.finalframework.spring.web.handler.exception;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.exception.IException;
import com.ilikly.finalframework.data.result.R;
import com.ilikly.finalframework.data.result.Result;
import com.ilikly.finalframework.spring.web.handler.exception.annotation.RestExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Slf4j
@Order
@RestExceptionHandler
public class DefaultResultExceptionHandler implements ResultExceptionHandler<Throwable> {

    @Override
    public boolean supports(Throwable t) {
        return true;
    }

    @Override
    public Result handle(Throwable throwable) {
        if (throwable instanceof IException) {
            return R.failure(((IException) throwable).getCode(), throwable.getMessage());
        }
        logger.error("UnCatchException:", throwable);
        return R.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), Assert.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }
}
