package cn.com.likly.finalframework.spring.web.exception;

@SuppressWarnings("unused")
public interface ExceptionHandler<E extends Throwable, R> {

    boolean isSupported(Throwable t);

    R handle(E e);
}
