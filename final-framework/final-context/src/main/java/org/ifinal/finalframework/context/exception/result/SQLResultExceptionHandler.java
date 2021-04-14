package org.ifinal.finalframework.context.exception.result;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.core.annotation.result.R;
import org.ifinal.finalframework.core.annotation.result.Result;

import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0.0
 * @see java.sql.SQLIntegrityConstraintViolationException
 * @since 1.0.0
 */
@Order(0)
@Component
public class SQLResultExceptionHandler implements ResultExceptionHandler<SQLException> {

    @Override
    public boolean supports(final @NonNull Throwable throwable) {

        return throwable instanceof SQLException;
    }

    @Override
    @NonNull
    public Result<?> handle(final @NonNull SQLException throwable) {

        return R.failure(500, throwable.getMessage(), throwable.getErrorCode() + "", throwable.getSQLState());
    }

}

