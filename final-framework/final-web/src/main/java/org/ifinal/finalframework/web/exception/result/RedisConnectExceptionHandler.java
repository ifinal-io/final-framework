package org.ifinal.finalframework.web.exception.result;

import io.lettuce.core.RedisConnectionException;
import org.ifinal.finalframework.context.exception.result.ResultExceptionHandler;
import org.ifinal.finalframework.origin.result.R;
import org.ifinal.finalframework.origin.result.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@ConditionalOnClass(RedisConnectionException.class)
public class RedisConnectExceptionHandler implements ResultExceptionHandler<RedisConnectionException> {

    @Override
    public boolean supports(final Throwable throwable) {

        return throwable instanceof RedisConnectionException;
    }

    @Override
    public Result<?> handle(final RedisConnectionException throwable) {

        return R.failure(500, "Redis 连接异常： " + throwable.getMessage());
    }

}
