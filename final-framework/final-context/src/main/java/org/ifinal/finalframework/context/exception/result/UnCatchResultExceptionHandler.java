package org.ifinal.finalframework.context.exception.result;

import org.ifinal.finalframework.annotation.core.result.R;
import org.ifinal.finalframework.annotation.core.result.Result;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order
@Component
public class UnCatchResultExceptionHandler implements ResultExceptionHandler<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(UnCatchResultExceptionHandler.class);

    @Override
    public boolean supports(Throwable t) {
        return true;
    }

    @Override
    public Result<?> handle(Throwable throwable) {
        logger.error("UnCatchException:", throwable);
        return R.failure(500, Asserts.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }
}
