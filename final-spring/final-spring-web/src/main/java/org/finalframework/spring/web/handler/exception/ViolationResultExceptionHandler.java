package org.finalframework.spring.web.handler.exception;

import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.web.handler.exception.annotation.RestExceptionHandler;
import org.springframework.http.HttpStatus;

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
@RestExceptionHandler
public class ViolationResultExceptionHandler implements ResultExceptionHandler {
    @Override
    public boolean supports(Throwable t) {
        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result handle(Throwable e) {
        ConstraintViolationException violationException = (ConstraintViolationException) e;
        return R.failure(
                HttpStatus.BAD_REQUEST.value(), violationException.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }
}
