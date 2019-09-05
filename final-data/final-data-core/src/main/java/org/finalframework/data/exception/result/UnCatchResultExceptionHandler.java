package org.finalframework.data.exception.result;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.core.Assert;
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
public class UnCatchResultExceptionHandler implements org.finalframework.data.exception.result.ResultExceptionHandler<Throwable> {

    @Override
    public boolean supports(Throwable t) {
        return true;
    }

    @Override
    public Result handle(Throwable throwable) {
        logger.error("UnCatchException:", throwable);
        return R.failure(500, Assert.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }
}
