package com.ilikly.finalframework.spring.web.handler.exception;

import com.ilikly.finalframework.data.result.R;
import com.ilikly.finalframework.data.result.Result;
import com.ilikly.finalframework.json.JsonException;
import com.ilikly.finalframework.spring.web.handler.exception.annotation.RestExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;

/**
 * Json 异常处理器，将业务层抛出的{@link JsonException}异常包装成 {@link Result}返回给前端。
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:20
 * @since 1.0
 * @see JsonException
 */
@RestExceptionHandler
@ConditionalOnClass(JsonException.class)
public class JsonResultExceptionHandler implements ResultExceptionHandler<JsonException> {
    @Override
    public boolean supports(@NonNull Throwable t) {
        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result handle(JsonException e) {
        return R.failure(400, e.getMessage());
    }
}
