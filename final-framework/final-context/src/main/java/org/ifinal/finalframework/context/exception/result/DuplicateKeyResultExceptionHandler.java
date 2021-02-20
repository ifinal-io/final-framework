package org.ifinal.finalframework.context.exception.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;

import org.ifinal.finalframework.annotation.core.result.R;
import org.ifinal.finalframework.annotation.core.result.Result;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @see DuplicateKeyException
 * @since 1.0.0
 */
@Order(0)
@Configuration
@ConditionalOnClass(DuplicateKeyException.class)
public class DuplicateKeyResultExceptionHandler implements ResultExceptionHandler<DuplicateKeyException> {

    @Override
    public boolean supports(final @NonNull Throwable throwable) {
        return throwable instanceof DuplicateKeyException;
    }

    @Override
    @NonNull
    public Result<?> handle(final @NonNull DuplicateKeyException throwable) {
        return R.failure(500, "Duplicate Key", "500", Optional.ofNullable(throwable.getMessage()).orElse(""));

    }

}

