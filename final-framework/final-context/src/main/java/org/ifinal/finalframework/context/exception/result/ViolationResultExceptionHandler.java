package org.ifinal.finalframework.context.exception.result;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.ifinal.finalframework.origin.result.R;
import org.ifinal.finalframework.origin.result.ResponseStatus;
import org.ifinal.finalframework.origin.result.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The handler is to handle the exception throw by framework of {@link javax.validation.Validator}
 *
 * <ul>
 *     <li>{@link javax.validation.constraints.AssertFalse}</li>
 *     <li>{@link javax.validation.constraints.AssertTrue}</li>
 *     <li>{@link javax.validation.constraints.DecimalMax}</li>
 *     <li>{@link javax.validation.constraints.DecimalMin}</li>
 *     <li>{@link javax.validation.constraints.Digits}</li>
 *     <li>{@link javax.validation.constraints.Email}</li>
 *     <li>{@link javax.validation.constraints.Future}</li>
 *     <li>{@link javax.validation.constraints.FutureOrPresent}</li>
 *     <li>{@link javax.validation.constraints.Max}</li>
 *     <li>{@link javax.validation.constraints.Min}</li>
 *     <li>{@link javax.validation.constraints.Negative}</li>
 *     <li>{@link javax.validation.constraints.NegativeOrZero}</li>
 *     <li>{@link javax.validation.constraints.NotBlank}</li>
 *     <li>{@link javax.validation.constraints.NotEmpty}</li>
 *     <li>{@link javax.validation.constraints.NotNull}</li>
 *     <li>{@link javax.validation.constraints.Null}</li>
 *     <li>{@link javax.validation.constraints.Past}</li>
 *     <li>{@link javax.validation.constraints.PastOrPresent}</li>
 *     <li>{@link javax.validation.constraints.Pattern}</li>
 *     <li>{@link javax.validation.constraints.Positive}</li>
 *     <li>{@link javax.validation.constraints.PositiveOrZero}</li>
 *     <li>{@link javax.validation.constraints.Size}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @see ConstraintViolationException
 * @see javax.validation.Valid
 * @since 1.0.0
 */
@Order(0)
@Component
@ConditionalOnClass(ConstraintViolationException.class)
public class ViolationResultExceptionHandler implements ResultExceptionHandler<ConstraintViolationException> {

    @Override
    public boolean supports(final Throwable t) {

        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result<?> handle(final ConstraintViolationException e) {

        return R.failure(
            ResponseStatus.BAD_REQUEST.getCode(), e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","))
        );
    }

}
