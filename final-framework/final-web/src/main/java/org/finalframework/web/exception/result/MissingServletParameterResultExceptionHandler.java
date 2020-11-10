package org.finalframework.web.exception.result;

import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.context.exception.result.ResultExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 将 {@link MissingServletRequestParameterException}异常转化为{@link Result}结果，自定义描述语。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-03 15:29:32
 * @see MissingServletRequestParameterException
 * @since 1.0
 */
@Component
public class MissingServletParameterResultExceptionHandler implements ResultExceptionHandler<MissingServletRequestParameterException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result<?> handle(MissingServletRequestParameterException throwable) {
        MissingServletRequestParameterException e = throwable;
        return R.failure(400, e.getMessage());
    }
}
