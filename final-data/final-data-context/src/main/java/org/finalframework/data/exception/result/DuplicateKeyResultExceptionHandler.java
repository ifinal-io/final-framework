package org.finalframework.data.exception.result;


import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-08 16:59:32
 * @see DuplicateKeyException
 * @since 1.0
 */
@Order(0)
@SpringComponent
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

