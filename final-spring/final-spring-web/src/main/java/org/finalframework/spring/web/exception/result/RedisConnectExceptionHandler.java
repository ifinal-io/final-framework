package org.finalframework.spring.web.exception.result;

import io.lettuce.core.RedisConnectionException;
import org.finalframework.data.exception.handler.ExceptionHandler;
import org.finalframework.data.exception.result.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 11:31:21
 * @since 1.0
 */
@SpringComponent
@ConditionalOnClass(RedisConnectionException.class)
public class RedisConnectExceptionHandler implements ResultExceptionHandler<RedisConnectionException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof RedisConnectionException;
    }

    @Override
    public Result<?> handle(RedisConnectionException throwable) {
        return R.failure(500, "Redis 连接异常： " + throwable.getMessage());
    }
}
