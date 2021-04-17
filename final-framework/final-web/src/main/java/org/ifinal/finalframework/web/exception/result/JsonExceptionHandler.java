package org.ifinal.finalframework.web.exception.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.context.exception.result.ResultExceptionHandler;
import org.ifinal.finalframework.core.result.R;
import org.ifinal.finalframework.core.result.Result;
import org.ifinal.finalframework.json.JsonException;

/**
 * Json 异常处理器，将业务层抛出的{@link JsonException}异常包装成 {@link Result}返回给前端。
 *
 * @author likly
 * @version 1.0.0
 * @see JsonException
 * @since 1.0.0
 */
@Component
@ConditionalOnClass(JsonException.class)
public class JsonExceptionHandler implements ResultExceptionHandler<JsonException> {

    @Override
    public boolean supports(final @NonNull Throwable t) {

        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result<?> handle(final JsonException e) {

        return R.failure(400, e.getMessage());
    }

}
