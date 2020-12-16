package org.ifinal.finalframework.annotation.query;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.origin.Pageable;
import org.ifinal.finalframework.origin.lang.Transient;

/**
 * 分页查询
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@Transient
public class PageQuery implements Pageable, Serializable {

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
     * 分页页面
     */
    @Page
    private Integer page = DEFAULT_PAGE;

    /**
     * 页面宅师
     */
    @Size
    private Integer size = DEFAULT_SIZE;

    /**
     * 是否启用Count统计
     */
    private Boolean count = Boolean.TRUE;

}
