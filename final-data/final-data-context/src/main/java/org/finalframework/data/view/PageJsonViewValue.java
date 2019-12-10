package org.finalframework.data.view;


import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.entity.IPage;
import org.finalframework.data.result.Page;
import org.finalframework.data.result.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:57:58
 * @see JsonView
 * @see JsonViewValue
 * @see Page
 * @since 1.0
 */
public class PageJsonViewValue<T extends Serializable> extends PageInfo implements IPage<JsonViewValue<List<T>>> {

    private static final long serialVersionUID = -7123032399172554094L;

    private final JsonViewValue<List<T>> result;

    public PageJsonViewValue(Page<T> page, Class<?> view) {
        this.setPage(page.getPage());
        this.setSize(page.getSize());
        this.setPages(page.getPages());
        this.setTotal(page.getTotal());
        this.setFirstPage(page.getFirstPage());
        this.setLastPage(page.getLastPage());
        this.result = new JsonViewValue<>(page.getResult(), view);
    }

    @Override
    public JsonViewValue<List<T>> getResult() {
        return result;
    }
}

