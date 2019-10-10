package org.finalframework.data.exception;


/**
 * 未捕获的异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-15 10:29:02
 * @since 1.0
 */
public class UnCatchException extends RuntimeException {
    public UnCatchException(Throwable cause) {
        super(cause);
    }
}
