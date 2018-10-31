package cn.com.likly.finalframework.spring.web.exception;

import cn.com.likly.finalframework.data.exception.IException;
import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import cn.com.likly.finalframework.spring.annotation.RestExceptionHandler;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Order
@RestExceptionHandler
public class DefaultResultExceptionHandler implements ResultExceptionHandler<Throwable> {

    @Override
    public boolean isSupported(Throwable t) {
        return true;
    }

    @Override
    public Result handle(Throwable throwable) {
        if (throwable instanceof IException) {
            return R.failure(((IException) throwable).getCode(), throwable.getMessage());
        }


        return R.failure(500, throwable.getMessage());
    }
}
