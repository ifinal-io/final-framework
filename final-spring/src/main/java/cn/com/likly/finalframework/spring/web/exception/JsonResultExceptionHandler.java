package cn.com.likly.finalframework.spring.web.exception;

import cn.com.likly.finalframework.data.json.JsonException;
import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import cn.com.likly.finalframework.spring.annotation.RestExceptionHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:20
 * @since 1.0
 */
@RestExceptionHandler
public class JsonResultExceptionHandler implements ResultExceptionHandler<JsonException> {
    @Override
    public boolean supports(Throwable t) {
        return t instanceof JsonException;
    }

    @Override
    public Result handle(JsonException e) {
        return R.failure(400, e.getMessage());
    }
}
