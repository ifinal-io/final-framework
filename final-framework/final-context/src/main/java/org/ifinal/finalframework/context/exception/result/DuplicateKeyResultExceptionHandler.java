package org.ifinal.finalframework.context.exception.result;

import java.util.Optional;
import org.ifinal.finalframework.origin.result.R;
import org.ifinal.finalframework.origin.result.Result;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

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
    public boolean supports(final Throwable throwable) {

        return throwable instanceof DuplicateKeyException;
    }

    @Override
    public Result<?> handle(final DuplicateKeyException throwable) {

        return R.failure(500, "Duplicate Key", "500", Optional.ofNullable(throwable.getMessage()).orElse(""));

    }

}

