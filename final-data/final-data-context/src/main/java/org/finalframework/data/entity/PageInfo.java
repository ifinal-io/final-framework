package org.finalframework.data.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 17:00:44
 * @since 1.0
 */
@Data
public class PageInfo implements Serializable {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 页面容量
     */
    private Integer size;
    /**
     * 页数
     */
    private Integer pages;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 是否首页
     */
    private Boolean firstPage;
    /**
     * 是否尾页
     */
    private Boolean lastPage;

    public PageInfo toPageInfo() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.page = this.page;
        pageInfo.size = this.size;
        pageInfo.pages = this.pages;
        pageInfo.total = this.total;
        pageInfo.firstPage = this.firstPage;
        pageInfo.lastPage = this.lastPage;
        return pageInfo;
    }
}

