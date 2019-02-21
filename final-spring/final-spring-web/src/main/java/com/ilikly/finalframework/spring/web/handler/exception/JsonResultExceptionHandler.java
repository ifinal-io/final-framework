package com.ilikly.finalframework.spring.web.handler.exception;

import com.ilikly.finalframework.data.result.R;
import com.ilikly.finalframework.data.result.Result;
import com.ilikly.finalframework.json.JsonException;
import com.ilikly.finalframework.spring.web.handler.exception.annotation.RestExceptionHandler;

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
