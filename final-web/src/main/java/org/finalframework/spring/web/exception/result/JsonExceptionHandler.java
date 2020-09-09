

package org.finalframework.spring.web.exception.result;

import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.context.exception.result.ResultExceptionHandler;
import org.finalframework.json.JsonException;
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
@SpringComponent
@ConditionalOnClass(JsonException.class)
public class JsonExceptionHandler implements ResultExceptionHandler<JsonException> {
    @Override
    public boolean supports(@NonNull Throwable t) {
        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result<?> handle(JsonException e) {
        return R.failure(400, e.getMessage());
    }
}
