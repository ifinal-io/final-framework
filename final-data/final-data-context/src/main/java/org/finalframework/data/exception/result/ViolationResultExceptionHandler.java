package org.finalframework.data.exception.result;

import org.finalframework.data.response.ResponseStatus;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

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
@SpringComponent
@ConditionalOnClass(ConstraintViolationException.class)
public class ViolationResultExceptionHandler implements ResultExceptionHandler<ConstraintViolationException> {
    @Override
    public boolean supports(Throwable t) {
        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result<?> handle(ConstraintViolationException e) {
        return R.failure(
                ResponseStatus.BAD_REQUEST.getCode(), e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }
}
