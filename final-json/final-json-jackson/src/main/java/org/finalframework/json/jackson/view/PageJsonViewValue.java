package org.finalframework.json.jackson.view;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.result.Page;
import org.finalframework.data.result.PageInfo;
import org.finalframework.data.result.Viewable;
import org.finalframework.json.jackson.serializer.JsonViewValueSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:57:58
 * @see JsonView
 * @see JsonViewValue
 * @see JsonViewValueSerializer
 * @see Page
 * @since 1.0
 */
public class PageJsonViewValue<T extends Serializable> extends JsonViewValue<JsonViewValue<List<T>>> implements Viewable, Serializable {

    private static final long serialVersionUID = -7123032399172554094L;
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
     * 是否首页
     */
    private final Boolean firstPage;
    /**
     * 是否尾页
     */
    private final Boolean lastPage;

    @JsonIgnore
    private final PageInfo pageInfo;


    public PageJsonViewValue(Page<T> page, Class<?> view) {
        super(new JsonViewValue<>(page.getResult(), view), view);
        this.pageInfo = page.toPageInfo();
        this.page = this.pageInfo.getPage();
        this.size = this.pageInfo.getSize();
        this.pages = this.pageInfo.getPages();
        this.total = this.pageInfo.getTotal();
        this.firstPage = this.pageInfo.getFirstPage();
        this.lastPage = this.pageInfo.getLastPage();
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPages() {
        return pages;
    }

    public Long getTotal() {
        return total;
    }

    public Boolean getFirstPage() {
        return firstPage;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }
}

