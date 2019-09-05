package org.finalframework.data.exception.result;

import org.finalframework.data.exception.CommonServiceException;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * The handler is to handle the exception throw by framework of {@link javax.validation.Validator}
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:25
 * @see ConstraintViolationException
 * @since 1.0
 */
@ResultExceptionHandler
public class ViolationResultExceptionHandler implements org.finalframework.data.exception.result.ResultExceptionHandler<ConstraintViolationException> {
    @Override
    public boolean supports(Throwable t) {
        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result handle(ConstraintViolationException e) {
        return R.failure(
                CommonServiceException.BAD_REQUEST.getCode(), ((ConstraintViolationException) e).getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }
}
