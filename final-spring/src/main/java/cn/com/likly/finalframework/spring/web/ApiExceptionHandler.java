package cn.com.likly.finalframework.spring.web;

import cn.com.likly.finalframework.data.exception.IException;
import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handlerException(Exception e) {
        if (e instanceof IException) {
            return R.failure(((IException) e).getCode(), e.getMessage());
        } else if (e instanceof ConstraintViolationException) {
            /*
             * 验证异常
             */
            final String message = e.getMessage();
            return R.failure(HttpStatus.BAD_REQUEST.value(), message.substring(message.indexOf(":") + 1).trim());
        }

        logger.error("UnCatchException", e);
        return R.failure(-1, "UnCatchException:" + e.getMessage());
    }
}
