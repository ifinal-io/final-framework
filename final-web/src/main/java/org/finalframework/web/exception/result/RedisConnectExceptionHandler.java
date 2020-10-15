package org.finalframework.web.exception.result;

import io.lettuce.core.RedisConnectionException;
import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.context.exception.result.ResultExceptionHandler;
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
