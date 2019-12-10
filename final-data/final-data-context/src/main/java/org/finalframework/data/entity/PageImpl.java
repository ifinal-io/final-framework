package org.finalframework.data.entity;


/**
 * @author likly
 * @version 1.0
 * @date 2019-12-10 17:12:50
 * @since 1.0
 */
public class PageImpl<T> extends PageInfo implements IPage<T> {
    private T result;

    @Override
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

