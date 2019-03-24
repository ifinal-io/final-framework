package org.finalframework.spring.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.core.Assert;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.web.exception.annotation.RestExceptionHandler;
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
public class UnCatchResultExceptionHandler implements ResultExceptionHandler {

    @Override
    public boolean supports(Throwable t) {
        return true;
    }

    @Override
    public Result handle(Throwable throwable) {
        logger.error("UnCatchException:", throwable);
        return R.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), Assert.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }
}
