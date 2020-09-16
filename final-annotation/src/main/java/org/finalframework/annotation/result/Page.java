package org.finalframework.annotation.result;


import org.finalframework.annotation.IPage;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-10 17:12:50
 * @since 1.0
 */
public class Page<T> extends Pagination implements IPage<T> {
    private List<T> data;

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }
}

