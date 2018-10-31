package cn.com.likly.finalframework.spring.web.exception;

import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import cn.com.likly.finalframework.spring.annotation.RestExceptionHandler;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:25
 * @since 1.0
 */
@RestExceptionHandler
public class ViolationResultException implements ResultExceptionHandler<ConstraintViolationException> {
    @Override
    public boolean isSupported(Throwable t) {
        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result handle(ConstraintViolationException e) {
        return R.failure(
                HttpStatus.BAD_REQUEST.value(), e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }
}
