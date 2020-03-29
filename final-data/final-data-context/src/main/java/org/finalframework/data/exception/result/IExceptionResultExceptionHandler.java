package org.finalframework.data.exception.result;

import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.ServiceException;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@SpringComponent
public class IExceptionResultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(IExceptionResultExceptionHandler.class);

    public Result<?> handle(ServiceException e) {
        return R.failure(e.getStatus(), e.getDescription(), e.getCode(), e.getMessage());
    }

    public Result<?> handle(IException e) {
        return R.failure(500, e.getMessage(), e.getCode(), e.getMessage());
    }
}
