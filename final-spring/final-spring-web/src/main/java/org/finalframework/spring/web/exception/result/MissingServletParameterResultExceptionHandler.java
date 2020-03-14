package org.finalframework.spring.web.exception.result;

import org.finalframework.data.exception.result.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
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
@org.finalframework.data.exception.annotation.ResultExceptionHandler
public class MissingServletParameterResultExceptionHandler implements ResultExceptionHandler<MissingServletRequestParameterException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result handle(MissingServletRequestParameterException throwable) {
        MissingServletRequestParameterException e = throwable;
        return R.failure(400, e.getMessage());
    }
}
