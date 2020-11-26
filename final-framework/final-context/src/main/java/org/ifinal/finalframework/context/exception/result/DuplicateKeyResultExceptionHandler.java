package org.ifinal.finalframework.context.exception.result;


import org.ifinal.finalframework.annotation.result.R;
import org.ifinal.finalframework.annotation.result.Result;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @see DuplicateKeyException
 * @since 1.0.0
 */
@Order(0)
@Component
public class DuplicateKeyResultExceptionHandler implements ResultExceptionHandler<DuplicateKeyException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof DuplicateKeyException;
    }

    @Override
    public Result<?> handle(DuplicateKeyException throwable) {
        return R.failure(500, "Duplicate Key", "500", Optional.ofNullable(throwable.getMessage()).orElse(""));

    }
}

