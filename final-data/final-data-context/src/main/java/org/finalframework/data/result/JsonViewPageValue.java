package org.finalframework.data.result;


import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 11:26:21
 * @since 1.0
 */
@Data
public class JsonViewPageValue<T> implements Serializable {
    /**
     * 页码
     */
    private final Integer page;
    /**
     * 页面容量
     */
    private final Integer size;
    /**
     * 页数
     */
    private final Integer pages;
    /**
     * 总条数
     */
    private final Long total;
    /**
     * 结果集
     */
    private final JsonViewValue<Collection<T>> result;
    /**
     * 是否首页
     */
    private final Boolean firstPage;
    /**
     * 是否尾页
     */
    private final Boolean lastPage;

    public JsonViewPageValue(Page page, Class<?> view) {
        this.page = page.getPage();
        this.pages = page.getPages();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.firstPage = page.getFirstPage();
        this.lastPage = page.getLastPage();
        this.result = new JsonViewValue<>(page.getResult(), view);
    }
}

