package org.finalframework.spring.aop;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 10:06:36
 * @since 1.0
 */
public class OperationException extends RuntimeException {
    private final Throwable original;

    public OperationException(Throwable original) {
        super(original.getMessage(), original);
        this.original = original;
    }

    public Throwable getOriginal() {
        return original;
    }
}
