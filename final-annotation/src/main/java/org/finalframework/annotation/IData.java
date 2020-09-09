package org.finalframework.annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/9 17:57:02
 * @since 1.0
 */
public interface IData<T> {

    void setData(T data);

    T getData();
}
