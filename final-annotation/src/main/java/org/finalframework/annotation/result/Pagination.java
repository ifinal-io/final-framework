

package org.finalframework.annotation.result;


import lombok.Data;
import org.finalframework.annotation.IPagination;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 17:00:44
 * @since 1.0
 */
@Data
public class Pagination implements IPagination {
    private static final long serialVersionUID = -4875155337971995663L;
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

    public Pagination toPageInfo() {
        Pagination pagination = new Pagination();
        pagination.page = this.page;
        pagination.size = this.size;
        pagination.pages = this.pages;
        pagination.total = this.total;
        pagination.firstPage = this.firstPage;
        pagination.lastPage = this.lastPage;
        return pagination;
    }
}

