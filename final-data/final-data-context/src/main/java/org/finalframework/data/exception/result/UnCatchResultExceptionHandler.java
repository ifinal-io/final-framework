package org.finalframework.data.exception.result;

import org.finalframework.core.Assert;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Order
@SpringComponent
public class UnCatchResultExceptionHandler implements ResultExceptionHandler<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(UnCatchResultExceptionHandler.class);

    @Override
    public boolean supports(Throwable t) {
        return true;
    }

    @Override
    public Result<?> handle(Throwable throwable) {
        logger.error("UnCatchException:", throwable);
        return R.failure(500, Assert.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }
}
