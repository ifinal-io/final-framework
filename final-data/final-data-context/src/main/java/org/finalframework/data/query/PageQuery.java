package org.finalframework.data.query;

import org.finalframework.core.Assert;

import java.io.Serializable;

/**
 * 分页查询
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-12 12:41:24
 * @since 1.0
 */
public class PageQuery implements IQuery, Pageable, Queryable, Serializable {

    private static final long serialVersionUID = 4813020012879522797L;
    /**
     * 默认分页页码
     */
    private static final Integer DEFAULT_PAGE = 1;
    /**
     * 默认分页容量
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认启用Count统计
     */
    private static final Boolean DEFAULT_COUNT = Boolean.TRUE;

    /**
     * 分页页面
     */
    private Integer page = DEFAULT_PAGE;
    /**
     * 页面宅师
     */
    private Integer size = DEFAULT_SIZE;
    /**
     * 是否启用Count统计
     */
    private Boolean count = DEFAULT_COUNT;
    /**
     * 是否启用分页合理化
     */
    private Boolean reasonable = Boolean.TRUE;

    private Boolean pageSizeZero;

    @Override
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    @Override
    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

    @Override
    public Boolean getPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
    }

    @Override
    public Query convert() {
        final Query query = new Query();
        if (Assert.nonNull(page) && Assert.nonNull(size)) {
            query.page(page, size);
        }
        return query;
    }
}
