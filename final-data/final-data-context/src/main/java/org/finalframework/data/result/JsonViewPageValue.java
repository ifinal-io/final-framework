package org.finalframework.data.result;


import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 11:26:21
 * @since 1.0
 */
public class JsonViewPageValue<T> extends PageInfo implements Serializable {
    /**
     * 结果集
     */
    private JsonViewValue<Collection<T>> value;

    public JsonViewPageValue() {
    }

    public JsonViewPageValue(Page page, Class<?> view) {
        this.setPage(page.getPage());
        this.setSize(page.getSize());
        this.setPages(page.getPages());
        this.setTotal(page.getTotal());
        this.setFirstPage(page.getFirstPage());
        this.setLastPage(page.getLastPage());
        this.value = new JsonViewValue<>(page.getResult(), view);
    }

    public void setValue(JsonViewValue<Collection<T>> value) {
        this.value = value;
    }

    public JsonViewValue<Collection<T>> getValue() {
        return value;
    }
}

