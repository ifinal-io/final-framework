package org.finalframework.spring.web.exception.result;

import io.lettuce.core.RedisConnectionException;
import org.finalframework.data.exception.ExceptionHandler;
import org.finalframework.data.exception.annotation.ResultExceptionHandler;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 11:31:21
 * @since 1.0
 */
@AutoConfigureBefore(ResultGlobalResultExceptionHandler.class)
@ResultExceptionHandler
@ConditionalOnClass(RedisConnectionException.class)
public class RedisConnectExceptionHandler implements ExceptionHandler<RedisConnectionException, Result> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof RedisConnectionException;
    }

    @Override
    public Result handle(RedisConnectionException throwable) {
        return R.failure(500, "Redis 连接异常： " + throwable.getMessage());
    }
}
