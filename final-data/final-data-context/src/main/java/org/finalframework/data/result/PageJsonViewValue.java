package org.finalframework.data.result;


import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:57:58
 * @since 1.0
 */
public class PageJsonViewValue<T extends Serializable> extends PageInfo implements Viewable, Serializable {

    private static final long serialVersionUID = -7123032399172554094L;

    private final JsonViewValue<List<T>> value;
    private final Class<?> view;

    public PageJsonViewValue(Page<T> page, Class<?> view) {
        this.setPage(page.getPage());
        this.setSize(page.getSize());
        this.setTotal(page.getTotal());
        this.setPages(page.getPages());
        this.setFirstPage(page.getFirstPage());
        this.setLastPage(page.getLastPage());
        this.value = new JsonViewValue<>(page.getResult(), view);
        this.view = view;
    }

    @Override
    public Class<?> getView() {
        return view;
    }


    @Override
    public JsonViewValue<List<T>> getValue() {
        return value;
    }
}

