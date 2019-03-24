package org.finalframework.spring.web.exception;

import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.json.JsonException;
import org.finalframework.spring.web.exception.annotation.RestExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;

/**
 * Json 异常处理器，将业务层抛出的{@link JsonException}异常包装成 {@link Result}返回给前端。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:20
 * @see JsonException
 * @since 1.0
 */
@RestExceptionHandler
@ConditionalOnClass(JsonException.class)
public class JsonResultExceptionHandler implements ResultExceptionHandler {
    @Override
    public boolean supports(@NonNull Throwable t) {
        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result handle(Throwable e) {
        return R.failure(400, e.getMessage());
    }
}
