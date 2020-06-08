package org.finalframework.data.exception.result;


import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.core.annotation.Order;

import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-08 16:51:11
 * @see java.sql.SQLIntegrityConstraintViolationException
 * @since 1.0
 */
@Order(0)
@SpringComponent
public class SQLResultExceptionHandler implements ResultExceptionHandler<SQLException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof SQLException;
    }

    @Override
    public Result<?> handle(SQLException throwable) {
        return R.failure(500, throwable.getMessage(), throwable.getErrorCode() + "", throwable.getSQLState());
    }
}

