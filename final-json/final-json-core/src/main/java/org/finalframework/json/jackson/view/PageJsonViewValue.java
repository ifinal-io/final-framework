package org.finalframework.json.jackson.view;


import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.entity.PageImpl;
import org.finalframework.data.result.Page;

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
public class PageJsonViewValue<T extends Serializable> extends PageImpl<JsonViewValue<List<T>>> {

    private static final long serialVersionUID = -7123032399172554094L;

    public PageJsonViewValue(Page<T> page, Class<?> view) {
        this.setPage(page.getPage());
        this.setSize(page.getSize());
        this.setPages(page.getPages());
        this.setTotal(page.getTotal());
        this.setFirstPage(page.getFirstPage());
        this.setLastPage(page.getLastPage());
        this.setResult(new JsonViewValue<>(page.getResult(), view));
    }
}

