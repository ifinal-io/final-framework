package org.ifinal.finalframework.context.exception.result;

import java.sql.SQLException;
import org.ifinal.finalframework.origin.result.R;
import org.ifinal.finalframework.origin.result.Result;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    public boolean supports(final Throwable throwable) {

        return throwable instanceof SQLException;
    }

    @Override
    public Result<?> handle(final SQLException throwable) {

        return R.failure(500, throwable.getMessage(), throwable.getErrorCode() + "", throwable.getSQLState());
    }

}

