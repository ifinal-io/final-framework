package org.ifinal.finalframework.context.exception.result;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.core.result.R;
import org.ifinal.finalframework.core.result.Result;
import org.ifinal.finalframework.util.Asserts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public boolean supports(final Throwable t) {

        return true;
    }

    @Override
    public Result<?> handle(final Throwable throwable) {

        logger.error("UnCatchException:", throwable);
        return R.failure(500, Asserts.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }

}
