package cn.com.likly.finalframework.spring.handler.exception;

@SuppressWarnings("unused")
public interface ExceptionHandler<E extends Throwable, R> {

    boolean supports(Throwable t);

    R handle(E e);
}
