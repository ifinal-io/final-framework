package cn.com.likly.finalframework.spring.web.exception;

public interface ExceptionHandler<E extends Throwable, R> {

    boolean isSupported(E e);

    R handle(E e);
}
